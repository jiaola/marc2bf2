package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.mappings.MappingsReader;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class Field007Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field007Converter.class);
    private Map<String, Map> mappings;

    public Field007Converter(Model model, Record record) throws IOException {
        super(model, record);
        try {
            mappings = (Map<String, Map>) MappingsReader.readMappings("007.yml");
        } catch (IOException ex) {
            logger.error("Could not load mappings file for 007 field");
            throw ex;
        }
    }


    public Model convert(VariableField field) {
        if (!field.getTag().equals("007")) {
            return model;
        }
        model = convertInMode(((ControlField)field).getData(), "Work");
        //model = convertInMode(((ControlField)field).getData(), "Instance");
        return model;
    }

    private Model convertInMode(String data, String mode) {
        char[] chars = data.toCharArray();
        String c00 = String.valueOf(chars[0]);  // the first character in 007
        Resource resource = mode.equals("Work") ?
                ModelUtils.getWork(model, record) :
                ModelUtils.getInstance(model, record);

        Map nodeMap = ((Map<String, Map>) mappings.get(mode)).get(c00);
        // If there's no mapping for the character, skip it.
        if (nodeMap == null) return model;

        // If it's for Work, set the rdf:type of the Work
        if (mode.equals("Work") && nodeMap.containsKey("type")) {
            List<String> checks = (List<String>) nodeMap.getOrDefault("leader", new ArrayList<String>());
            if (!checks.contains(String.valueOf(record.getLeader().getTypeOfRecord()))) {
                resource.addProperty(RDF.type, model.createResource(BIB_FRAME.NAMESPACE + nodeMap.get("type")));
            }
        }

        if (!nodeMap.containsKey("positions")) {
            return model;
        }
        // Look at each positions, and convert them to nodes
        List<Map> positions = (List<Map>) nodeMap.get("positions");
        for (Map position: positions) {
            if (!position.containsKey("values")) continue;
            int pos = (int) position.get("position"); // the position of the character

            Map posMap = getPositionMapping(c00, pos); // Map from position to labels and uris
            Map<String, String> labels = (Map<String, String>) posMap.get("labels");
            Map<String, String> uris = (Map<String, String>) posMap.get("uris");
            if (labels == null && uris == null) continue; // Skip if there's no mappings

            String cpos = String.valueOf(chars[pos]);  // the character at this position
            List<String> values = (List<String>) position.get("values");  // Values to check
            if (!values.contains(cpos)) continue; // Skip if no need to process this character

            addDefault(position, resource); // if there's default label, add it.

            // In some special cases, the prefix is different from the default ones.
            // See k, pos 01
            String prefix = Optional.ofNullable(getPositionPrefix(nodeMap)).orElse((String) posMap.get("prefix"));

            Resource object;
            if (uris != null && uris.containsKey(cpos)) {
                String uri = mappings.get("vocabularies").get(prefix) + uris.get(cpos);
                object = model.createResource(uri);
            } else {
                object = model.createResource();
            }
            object.addProperty(RDF.type, model.createResource(BIB_FRAME.NAMESPACE + posMap.get("type")));
            if (labels != null && labels.containsKey(cpos)) {
                object.addProperty(RDFS.label, labels.get(cpos));
            }
            resource.addProperty(model.createProperty(BIB_FRAME.NAMESPACE, (String) posMap.get("property")), object);
        }

        return model;
    }

    private String getPositionPrefix(Map nodeMap) {
        Map<String, String> prefixes = (Map<String, String>) nodeMap.get("prefixes");
        return prefixes == null? null : prefixes.get("cpos");
    }

    private void addDefault(Map position, Resource resource) {
        if (position.containsKey("default")) {
            Map<String, String> defaultMap = (Map<String, String>) position.get("default");
            String uri = mappings.get("vocabularies").get(defaultMap.get("prefix")) + defaultMap.get("uri");
            Resource object = model.createResource(uri);
            object.addProperty(RDF.type, model.createResource(BIB_FRAME.NAMESPACE + defaultMap.get("type")));
            if (defaultMap.containsKey("label")) {
                object.addProperty(RDFS.label, defaultMap.get("label"));
            }
            resource.addProperty(model.createProperty(BIB_FRAME.NAMESPACE, defaultMap.get("property")), object);
        }
    }

    private Map getPositionMapping(String c00, int position) {
        List<Map> maps = (List<Map>) mappings.get("mappings").get(c00);
        for (Map map: maps) {
            if (((int)map.get("position")) == position) {
                return map;
            }
        }
        return new HashMap<String, Object>();
    }
}
