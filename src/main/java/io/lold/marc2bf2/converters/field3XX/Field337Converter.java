package io.lold.marc2bf2.converters.field3XX;

import org.apache.jena.rdf.model.Model;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field337Converter extends Field336Converter {
    public Field337Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "337".equals(field.getTag());
    }
}
