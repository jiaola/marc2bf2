package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.mappers.Mapper;
import io.lold.marc2bf2.mappings.MappingsReader;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.datatypes.xsd.impl.XSDDateType;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class Field006008Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field006008Converter.class);
    protected Map<String, Map> mappings;
    public Field006008Converter(Model model, Record record) throws Exception {
        super(model, record);
        try {
            mappings = (Map<String, Map>) MappingsReader.readMappings("006_008");
        } catch (IOException ex) {
            logger.error("Could not load mappings file for 006, 008 field");
            throw ex;
        }
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        String tag = field.getTag();
        if (!tag.equals("006") && !tag.equals("008")) {
            return model;
        }
        String data = ((ControlField) field).getData();

        Resource work = ModelUtils.getWork(model, record);

        convertInMode(data, tag, "AdminMetadata");
        convertInMode(data, tag, "Work");
        return model;
    }

    public void convertInMode(String data, String tag, String mode) throws Exception {
        Resource resource;
        if (mode.equals("Work")) {
            resource = ModelUtils.getWork(model, record);
        } else if (mode.equals("AdminMetadata")) {
            resource = ModelUtils.getAdminMatadata(model, record);
        } else if (model.equals("Instance")) {
            resource = ModelUtils.getInstance(model, record);
        } else {
            return;
        }
        Map<String, Object> mapping = (Map<String, Object>) ((Map)mappings.get(tag)).get(mode);
        if (mapping == null) return;

        RecordUtils.Material material = RecordUtils.getMaterialTypeFromLeader(record);
        processMaterialType(data, resource, mapping, material.toString());
        processMaterialType(data, resource, mapping, "Default");

    }

    private void processMaterialType(String data, Resource resource, Map<String, Object> mapping, String type) throws Exception {
        List<Map> positions = (List<Map>) mapping.get(type);
        if (positions == null) return;
        for (Map<String, Object> position: positions) {
            int pos = (int) position.get("position");
            int length = (int) position.getOrDefault("length", 1);
            if (data.length() < pos + length) {  // field data is too short.
                logger.warn("Can't locate position " + (pos+length) + " in " + data);
                continue;
            }
            String value = data.substring(pos, pos+length);
            Map m = (Map) mappings.getOrDefault("mappings", new HashMap())
                    .getOrDefault(position.get("mapping"), new HashMap());
            if (m == null) {
                logger.warn("No mapping found for position: " + pos);
            }
            Mapper mapper = Mapper.createMapper((String)position.get("mapper"), model);
            if (position.containsKey("params")) {
                mapper.setParams((Map)position.get("params"));
            }
            List<RDFNode> nodes = mapper.map(value, m);
            for (RDFNode node : nodes) {
                resource.addProperty(model.createProperty(BIB_FRAME.NAMESPACE, (String) m.get("property")), node);
            }
        }
    }
}
