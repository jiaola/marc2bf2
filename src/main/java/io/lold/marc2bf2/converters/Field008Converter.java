package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.mappers.Mapper;
import io.lold.marc2bf2.mappings.MappingsReader;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Field008Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field008Converter.class);
    private Map<String, Map> mappings;
    public Field008Converter(Model model, Record record) throws Exception {
        super(model, record);
        try {
            mappings = (Map<String, Map>) MappingsReader.readMappings("008");
        } catch (IOException ex) {
            logger.error("Could not load mappings file for 007 field");
            throw ex;
        }
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("008")) {
            return model;
        }
        String data = ((ControlField) field).getData();
        int marcYear = Integer.valueOf(data.substring(0, 2));
            String creationYear = marcYear < 50 ? "20" + marcYear : "19" + marcYear;
        String leader = record.getLeader().marshal();

        converToWork(data);
        return model;
    }

    public void converToWork(String data) throws Exception {
        Resource resource = ModelUtils.getWork(model, record);
        Map<String, Object> workMapping = mappings.get("Work");
        RecordUtils.Material material = RecordUtils.getMaterial(record);

        List<Map> positions = (List<Map>) workMapping.get(material.toString());
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
            List<RDFNode> nodes = mapper.map(value, m);
            for (RDFNode node : nodes) {
                resource.addProperty(model.createProperty(BIB_FRAME.NAMESPACE, (String) m.get("property")), node);
            }
        }

        data = data.substring(18, 35);
        if (RecordUtils.isBook(record)) {
            workBook(data);
        } else if (RecordUtils.isComputerFile(record)) {

        }
    }

    public void workBook(String data) {
        intendedAudience(data.substring(4, 5));
    }

    public void intendedAudience(String code) {

    }

    public void mapToBook() {

    }
}
