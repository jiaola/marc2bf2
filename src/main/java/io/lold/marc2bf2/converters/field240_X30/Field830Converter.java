package io.lold.marc2bf2.converters.field240_X30;

import org.apache.jena.rdf.model.Model;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field830Converter extends Field440Converter {
    public Field830Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "830".equals(field.getTag());
    }

}
