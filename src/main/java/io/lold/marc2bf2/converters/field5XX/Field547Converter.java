package io.lold.marc2bf2.converters.field5XX;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field547Converter extends Field500Converter {
    public Field547Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "547".equals(field.getTag());
    }

}
