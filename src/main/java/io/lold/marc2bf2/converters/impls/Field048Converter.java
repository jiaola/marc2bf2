package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.mappings.MappingsReader;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Field048Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field048Converter.class);
    Map<String, Map> mappings;

    public Field048Converter(Model model, Record record) throws Exception {
        super(model, record);
        try {
            mappings = (Map<String, Map>) MappingsReader.readMappings("048");
        } catch (IOException ex) {
            logger.error("Could not load mappings file for 007 field");
            throw ex;
        }
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;
        if (df.getIndicator2() != ' ') {
            return model;
        }

        Resource work = ModelUtils.getWork(model, record);
        List<Subfield> sfs = df.getSubfields("ab");
        for (Subfield sf: sfs) {
            String data = sf.getData();
            String code = StringUtils.substring(data, 0, 2);
            if (mappings.containsKey(code)) {
                Map mapping = mappings.get(code);
                String count = StringUtils.substring(data, 2, 4);
                Resource resource = model.createResource()
                        .addProperty(RDF.type, ModelUtils.getResource((String) mapping.get("type"), model));
                Map<String, String> props = (Map<String, String>) mapping.get("props");
                for (String prop: props.keySet()) {
                    resource.addProperty(ModelUtils.getProperty(prop, model), props.get(prop));
                }
                work.addProperty(ModelUtils.getProperty((String) mapping.get("property"), model), resource);
                if (StringUtils.isNotBlank(count)) {
                    resource.addProperty(BIB_FRAME.count, String.valueOf(NumberUtils.createInteger(count)));
                }
            }
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "048".equals(field.getTag());
    }
}
