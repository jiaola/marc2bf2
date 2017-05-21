package io.lold.marc2bf2.converters.impls;

import org.apache.jena.rdf.model.Model;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field811Converter extends Field800Converter {
    public Field811Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "811".equals(getTag(field));
    }
}
