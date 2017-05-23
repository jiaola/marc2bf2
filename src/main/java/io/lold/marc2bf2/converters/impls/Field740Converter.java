package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.impls.Field730Converter;
import org.apache.jena.rdf.model.Model;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field740Converter extends Field730Converter {
    public Field740Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "740".equals(getTag(field));
    }

}
