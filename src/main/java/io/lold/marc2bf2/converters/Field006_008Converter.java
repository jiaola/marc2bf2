package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.mappers.Mapper;
import io.lold.marc2bf2.mappings.MappingsReader;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.DataTypes;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class Field006_008Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field006_008Converter.class);
    protected Map<String, Map> mappings;
    public Field006_008Converter(Model model, Record record) throws Exception {
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

        convertInMode(data, tag, "AdminMetadata");
        convertInMode(data, tag, "Work");
        convertInMode(data, tag, "Instance");
        return model;
    }

    public void convertInMode(String data, String tag, String mode) throws Exception {
        Resource resource;
        if (mode.equals("Work")) {
            resource = ModelUtils.getWork(model, record);
        } else if (mode.equals("AdminMetadata")) {
            resource = ModelUtils.getAdminMatadata(model, record);
        } else if (mode.equals("Instance")) {
            resource = ModelUtils.getInstance(model, record);
        } else {
            return;
        }
        Map<String, Object> mapping = (Map<String, Object>) ((Map)mappings.get(tag)).get(mode);
        if (mapping == null) return;

        RecordUtils.Material material = "008".equals(tag) ?
                RecordUtils.getMaterialTypeFromLeader(record) :
                RecordUtils.getMaterialTypeFrom006(record);
        processMaterialType(data, resource, mapping, material.toString());
        processMaterialType(data, resource, mapping, "Default");

        if (tag.equals("008") && mode.equals("Instance") && RecordUtils.isBookByLeader(record)) {
            char char23 = data.charAt(23);
            char char24 = data.charAt(24);
            if (char23 == 'o' || char23 == 's') {
                if (char24 != 'm') {
                    resource.addProperty(RDF.type, BIB_FRAME.Electronic);
                }
            } else if (char23 == 'r') {
                resource.addProperty(RDF.type, BIB_FRAME.Print);
            }
        }

        // provision
        if (tag.equals("008") && mode.equals("Instance")) {
            provision(data);
        }

        // language
        if (tag.equals("008") && mode.equals("Work")) {
            String code = StringUtils.substring(data, 35, 38);
            if (StringUtils.isNotBlank(code) && !"|||".equals(code)) {
                String uri = ModelUtils.getUriWithNsPrefix("languages", code);
                resource.addProperty(BIB_FRAME.language, model.createResource(uri)
                        .addProperty(RDF.type, BIB_FRAME.Language));
            }
        }
    }

    private void provision(String data) {
        Resource instance = ModelUtils.getInstance(model, record);
        String char6 = data.substring(6, 7);
        String date = null;
        String part1 = StringUtils.substring(data, 7, 11);
        String part2 = StringUtils.substring(data, 11, 15);
        if (char6.equals("c")) {
            if (StringUtils.isNotBlank(part1)) {
                date = part1 + "/..";
            }
        } else if ("dikmqu".contains(char6)) {
            if (StringUtils.isNotBlank(part1) && StringUtils.isNotBlank(part2)) {
                date = part1 + "/" + part2;
            }
        } else if (char6.equals("e")) {
            if (StringUtils.isBlank(StringUtils.substring(data, 13, 15))) {
                if (StringUtils.isNotBlank(part1) && StringUtils.isNotBlank(part2)) {
                    date = part1 + "-" + part2;
                }
            } else {
                if (StringUtils.isNotBlank(part1) && StringUtils.isNotBlank(part2)) {
                    date = part1 + "-" + data.substring(11, 13) + "-" + data.substring(13, 15);
                }
            }
        } else if ("prst".contains(char6)) {
            if (StringUtils.isNotBlank(part1)) {
                date = part1;
            }
        } else if ("|".equals(char6)) {
            if (StringUtils.isNotBlank(part1) && StringUtils.isNotBlank(part2)) {
                date = part1 + "/" + part2;
            } else if (StringUtils.isNotBlank(part1)) {
                date = part1;
            }
        }
        if (StringUtils.isNotBlank(date)) {
            date = date.replace('u', 'x').replace('U', 'X');
        }
        String place = data.substring(15, 18).trim();

        Resource country = null;
        if (!StringUtils.isBlank(place)) {
            country = model.createResource("http://id.loc.gov/vocabulary/countries/" + place)
                    .addProperty(RDF.type, BIB_FRAME.Place);
        }

        if (StringUtils.isBlank(date)) {
            if (!StringUtils.isBlank(place)) {
                Resource prov = model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Publication)
                        .addProperty(RDF.type, BIB_FRAME.ProvisionActivity);
                if (country != null) {
                    prov.addProperty(BIB_FRAME.place, country);
                }
            }
        } else {
            Resource prov = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.ProvisionActivity);

            if ("cdemqrstu".contains(char6)) {
                prov.addProperty(RDF.type, BIB_FRAME.Publication);
            } else if ("ik".contains(char6)) {
                String label = char6.equals("i") ? "inclusive collection dates" : "bulk collection dates";
                Resource note = createLabeledResource(BIB_FRAME.note, label);
                prov.addProperty(RDF.type, BIB_FRAME.Production)
                        .addProperty(BIB_FRAME.note, note);
            } else if (char6.equals("p")) {
                prov.addProperty(RDF.type, BIB_FRAME.Distribution);
            }
            prov.addProperty(BIB_FRAME.date,
                    model.createTypedLiteral(date, DataTypes.EDTF));
            if (country != null) {
                prov.addProperty(BIB_FRAME.place, country);
            }
            instance.addProperty(BIB_FRAME.provisionActivity, prov);

            if (char6.equals("c")) {
                Resource note = createLabeledResource(BIB_FRAME.Note, "Currently published");
                instance.addProperty(BIB_FRAME.note, note);
            } else if (char6.equals("d")) {
                Resource note = createLabeledResource(BIB_FRAME.Note, "Ceased publication");
                instance.addProperty(BIB_FRAME.note, note);
            } else if (char6.equals("p")) {
                Resource p = model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.ProvisionActivity)
                        .addProperty(RDF.type, BIB_FRAME.Production);
                String date2 = data.substring(11, 15).replace('u', 'x').replace('U', 'X');
                p.addProperty(BIB_FRAME.date,
                        model.createTypedLiteral(date2, DataTypes.EDTF));
                instance.addProperty(BIB_FRAME.provisionActivity, p);
            } else if (char6.equals("t")) {
                String date2 = data.substring(11, 15).replace('u', 'x').replace('U', 'X');
                instance.addProperty(BIB_FRAME.copyrightDate,
                        model.createTypedLiteral(date2, DataTypes.EDTF));
            }
        }
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

            if (position.containsKey("values")) {
                List<String> values = (List<String>)position.get("values");
                if (!values.contains(value)) {
                    return;
                }
            }
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
