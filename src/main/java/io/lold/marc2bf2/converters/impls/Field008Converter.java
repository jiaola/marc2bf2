package io.lold.marc2bf2.converters.impls;

import org.apache.jena.rdf.model.Model;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import java.util.Map;

public class Field008Converter extends Field006Converter {
    protected Map<String, Map> mappings;
    public Field008Converter(Model model, Record record) throws Exception {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "008".equals(getTag(field));
    }
}
