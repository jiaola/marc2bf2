package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.mappers.DefaultLabelUriMapper;
import io.lold.marc2bf2.mappers.Field007Mapper;
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


    public Model convert(VariableField field) throws Exception{
        if (!field.getTag().equals("007")) {
            return model;
        }
        model = convertInMode(((ControlField)field).getData(), "Work");
        model = convertInMode(((ControlField)field).getData(), "Instance");
        return model;
    }

    private Model convertInMode(String data, String mode) throws Exception {
        String c00 = data.substring(0, 1);  // the first character in 007
        Resource resource = mode.equals("Work") ?
                ModelUtils.getWork(model, record) :
                ModelUtils.getInstance(model, record);

        Map nodeMap = ((Map<String, Map>) mappings.get(mode)).get(c00);
        // If there's no mapping for the character, skip it.
        if (nodeMap == null) return model;

        // If there is a type, set the rdf:type of the Work/Instance
        if (nodeMap.containsKey("type")) {
            List<String> checks = (List<String>) nodeMap.getOrDefault("leader", new ArrayList<String>());
            if (!checks.contains(String.valueOf(record.getLeader().getTypeOfRecord()))) {
                resource.addProperty(RDF.type, model.createResource(BIB_FRAME.NAMESPACE + nodeMap.get("type")));
            }
        }

        if (!nodeMap.containsKey("positions")) { // nothing else to map
            return model;
        }

        // Look at each positions, and convert them to nodes
        List<Map> positions = (List<Map>) nodeMap.get("positions");
        for (Map position: positions) {
            if (!position.containsKey("values") && !position.containsKey("mapper"))
                continue;

            // Sometimes it depends on whether a field exists in the record.
            // See Instance c, position: 1
            if (position.containsKey("condition")) {
                if (!record.getVariableFields((String)position.get("condition")).isEmpty()) {
                    continue;
                }
            }

            int pos = (int) position.get("position"); // the position of the character

            Map posMap = getPositionMapping(c00, pos); // Map from position to labels and uris
            Field007Mapper mapper;
            if (position.containsKey("mapper")) {
                String className = (String) position.get("mapper");
                Class<?> clazz = Class.forName(className);
                mapper = (Field007Mapper) clazz.newInstance();
            } else {
                Map<String, String> labels = (Map<String, String>) posMap.get("labels");
                Map<String, String> uris = (Map<String, String>) posMap.get("uris");
                if (labels == null && uris == null) continue; // Skip if there's no mappings
                mapper = new DefaultLabelUriMapper(labels, uris);
            }

            int length = (int) posMap.getOrDefault("length", 1);
            String cpos = data.substring(pos, pos+length); // the character(s) at this position
            List<String> values = (List<String>) position.get("values");  // Values to check
            if (values != null && !values.contains(cpos)) continue; // Skip if no need to process this character

            addDefault(position, resource); // if there's default label, add it.

            // In some special cases, the prefix is different from the default ones.
            // See k, pos 01
            String prefix = Optional.ofNullable(getPositionPrefix(nodeMap)).orElse((String) posMap.get("prefix"));

            String uri = mapper.mapToUri(cpos);
            String label = mapper.mapToLabel(cpos);
            Resource object = createNode(prefix, (String)posMap.get("type"), label, uri);
            resource.addProperty(model.createProperty(BIB_FRAME.NAMESPACE, (String) posMap.get("property")), object);

            if (position.containsKey("extra")) {
                Map<String, Map<String, String>> extraMap = (Map<String, Map<String, String>>)position.get("extra");
                mapper = new DefaultLabelUriMapper(extraMap.get("labels"), extraMap.get("uris"));
                String extraUri = mapper.mapToUri(cpos);
                String extraLabel = mapper.mapToLabel(cpos);
                Resource extraObject = createNode(prefix, (String)posMap.get("type"), extraLabel, extraUri);
                if (extraObject != null) {
                    resource.addProperty(model.createProperty(BIB_FRAME.NAMESPACE, (String) posMap.get("property")), extraObject);
                }
            }
        }

        if (mode.equals("Instance") && nodeMap.containsKey("media")) {
            if (record.getVariableFields("337").isEmpty()) {
                Map<String, String> mediaMap = (Map<String, String>) nodeMap.get("media");
                Resource media = model.createResource("http://id.loc.gov/vocabulary/mediaTypes/" + mediaMap.get("uri"));
                media.addProperty(RDF.type, BIB_FRAME.Media).addProperty(RDFS.label, mediaMap.get("label"));
                resource.addProperty(BIB_FRAME.media, media);
            }
        }
        return model;
    }

    private Resource createNode(String prefix, String type, String label, String uri) {
        if (label == null && uri == null) return null;
        Resource object;
        if (uri != null) {
            uri = mappings.get("vocabularies").get(prefix) + uri;
            object = model.createResource(uri);
        } else {
            object = model.createResource();
        }
        object.addProperty(RDF.type, model.createResource(BIB_FRAME.NAMESPACE + type));
        if (label != null) {
            object.addProperty(RDFS.label, label);
        }
        return object;
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
