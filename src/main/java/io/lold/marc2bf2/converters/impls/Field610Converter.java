package io.lold.marc2bf2.converters.impls;

import org.apache.jena.rdf.model.Model;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field610Converter extends Field600Converter {
    public Field610Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "610".equals(getTag(field));
    }
}
