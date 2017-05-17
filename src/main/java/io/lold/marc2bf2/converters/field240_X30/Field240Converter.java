package io.lold.marc2bf2.converters.field240_X30;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field240Converter extends Field130Converter {
    public Field240Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "240".equals(field.getTag());
    }

}
