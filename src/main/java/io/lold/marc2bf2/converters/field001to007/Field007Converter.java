package io.lold.marc2bf2.converters.field001to007;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.mappers.Mapper;
import io.lold.marc2bf2.mappings.MappingsReader;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
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
            mappings = (Map<String, Map>) MappingsReader.readMappings("007");
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

        Map config = ((Map<String, Map>) mappings.get(mode)).get(c00);
        // If there's no mapping for this type of field, skip it.
        if (config == null) return model;

        // If there is a type, set the rdf:type of the Work/Instance
        if (config.containsKey("type")) {
            List<String> checks = (List<String>) config.getOrDefault("leader", new ArrayList<String>());
            if (!checks.contains(String.valueOf(record.getLeader().getTypeOfRecord()))) {
                resource.addProperty(RDF.type, model.createResource(BIB_FRAME.NAMESPACE + config.get("type")));
            }
        }

        if (!config.containsKey("positions")) { // nothing else to map
            return model;
        }

        // Look at each positions, and map them to nodes
        List<Map> positions = (List<Map>) config.get("positions");
        for (Map position: positions) {
            int pos = (int) position.get("position"); // the position of the character
            Map mapping = getPositionMapping(c00, pos); // Map from position to labels and uris

            int length = (int) mapping.getOrDefault("length", 1);
            if (data.length() < pos + length) {  // field data is too short.
                continue;
            }

            String value = data.substring(pos, pos+length);

            if (position.containsKey("values")) { // if values configured, use it to filter
                List<String> values = (List<String>)position.get("values");
                if (!values.contains(value)) { // we don't need to map this value
                    continue;
                }
            }

            // Sometimes it depends on whether a field exists in the record.
            // See Instance c, position: 1
            if (position.containsKey("condition")) {
                if (!record.getVariableFields((String)position.get("condition")).isEmpty()) {
                    continue;
                }
            }

            // Create a mapper
            Mapper mapper = Mapper.createMapper((String)position.get("mapper"), model);
            mapper.setParams((Map)position.get("params"));

            // Add the tripples
            if (mapping.containsKey("mappings")) {
                List<Map> mappings = (List<Map>) mapping.get("mappings");
                for (Map m : mappings) {
                    addNodes(resource, m, value, mapper);
                }
            } else {
                addNodes(resource, mapping, value, mapper);
            }

            if (position.containsKey("default")) {
                addDefault(position, resource); // if there's default label, add it.
            }

            if (mode.equals("Instance") && config.containsKey("media")) {
                addMedia(config, resource);
            }
        }

        return model;
    }

    private void addNodes(Resource resource, Map mapping, String value, Mapper mapper) throws Exception {
        List<RDFNode> nodes = mapper.map(value, mapping);
        for (RDFNode node : nodes) {
            resource.addProperty(model.createProperty(BIB_FRAME.NAMESPACE, (String) mapping.get("property")), node);
        }
    }


    private void addDefault(Map position, Resource resource) throws Exception {
        Map<String, String> defaultMap = (Map<String, String>) position.get("default");
        String uri = ModelUtils.getUriWithNsPrefix(defaultMap.get("prefix"), defaultMap.get("uri"));
        Resource object = model.createResource(uri);
        object.addProperty(RDF.type, model.createResource(BIB_FRAME.NAMESPACE + defaultMap.get("type")));
        if (defaultMap.containsKey("label")) {
            object.addProperty(RDFS.label, defaultMap.get("label"));
        }
        resource.addProperty(model.createProperty(BIB_FRAME.NAMESPACE, defaultMap.get("property")), object);
    }

    private void addMedia(Map config, Resource resource) {
        if (!record.getVariableFields("337").isEmpty()) {
            return;
        }
        Map<String, String> mediaMap = (Map<String, String>) config.get("media");
        Resource media = model.createResource("http://id.loc.gov/vocabulary/mediaTypes/" + mediaMap.get("uri"));
        media.addProperty(RDF.type, BIB_FRAME.Media);
        if (mediaMap.containsKey("label")) {
            media.addProperty(RDFS.label, mediaMap.get("label"));
        }
        resource.addProperty(BIB_FRAME.media, media);
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
