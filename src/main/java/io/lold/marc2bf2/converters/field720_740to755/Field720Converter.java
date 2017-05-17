package io.lold.marc2bf2.converters.field720_740to755;

import io.lold.marc2bf2.converters.field1XX_6XX_7XX_8XX.Field7XXNameConverter;
import org.apache.jena.rdf.model.Model;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field720Converter extends Field7XXNameConverter {
    public Field720Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "720".equals(field.getTag());
    }

}
