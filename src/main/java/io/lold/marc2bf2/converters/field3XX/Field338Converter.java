package io.lold.marc2bf2.converters.field3XX;

import org.apache.jena.rdf.model.Model;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field338Converter extends Field336Converter {
    public Field338Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "338".equals(field.getTag());
    }
}
