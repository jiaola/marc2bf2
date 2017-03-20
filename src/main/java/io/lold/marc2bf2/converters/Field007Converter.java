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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Resource work = ModelUtils.getWork(model, record);
        // 1 position
        char[] chars = ((ControlField)field).getData().toCharArray();
        String c00 = String.valueOf(chars[0]);
        Map<String, Map> categoryMapping = (Map<String, Map>) mappings.get(c00);
        Map workMapping = ((Map<String, Map>) mappings.get("work")).get(c00);
        if (workMapping != null) {
            // Set work types based on the first character
            if (workMapping.containsKey("type")) {
                // check leader
                List<String> checks = (List<String>)workMapping.getOrDefault("leader", new ArrayList<String>());
                if (!checks.contains(String.valueOf(record.getLeader().getTypeOfRecord()))) {
                    work.addProperty(RDF.type, model.createResource(BIB_FRAME.NAMESPACE + workMapping.get("type")));
                }
            }
            // Set other formats based on the character positions
            if (workMapping.containsKey("positions")) {
                List<Map> positions = (List<Map>) workMapping.get("positions");
                for (Map position: positions) {
                    if (!position.containsKey("values")) {
                        continue;
                    }
                    int index = (int) position.get("position");
                    Map posMapping = getPositionMapping(c00, index);

                    Map<String, String> labels = (Map<String, String>) posMapping.get("labels");
                    Map<String, String> uris = (Map<String, String>) posMapping.get("uris");
                    if (labels == null && uris == null) {
                        continue;
                    }

                    String cpos = String.valueOf(chars[index]);
                    List<String> values = (List<String>) position.get("values");

                    if (!values.contains(cpos)) {
                        continue;
                    }

                    Resource resource;
                    if (uris != null && uris.containsKey(cpos)) {
                        String uri = mappings.get("vocabularies").get(posMapping.get("prefix")) + uris.get(cpos);
                        resource = model.createResource(uri);
                    } else {
                        resource = model.createResource();
                    }
                    resource.addProperty(RDF.type, model.createResource(BIB_FRAME.NAMESPACE + posMapping.get("type")));
                    if (labels != null && labels.containsKey(cpos)) {
                        resource.addProperty(RDFS.label, labels.get(cpos));
                    }
                    work.addProperty(model.createProperty(BIB_FRAME.NAMESPACE, (String) posMapping.get("property")), resource);
                }
            }
        }
        return model;
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
