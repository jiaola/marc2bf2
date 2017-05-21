package io.lold.marc2bf2.converters.impls;

import org.apache.jena.rdf.model.Model;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field711Converter extends Field700Converter {
    public Field711Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "711".equals(getTag(field));
    }
}
