package io.lold.marc2bf2.converters.field5XX;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field555Converter extends Field500Converter {
    public Field555Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("555")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        Resource note = buildResource(df, BIB_FRAME.Note);
        if (df.getIndicator1() == ' ') {
            note.addProperty(BIB_FRAME.noteType, "index");
        } else if (df.getIndicator1() == '0') {
            note.addProperty(BIB_FRAME.noteType, "finding aid");
        }
        instance.addProperty(BIB_FRAME.note, note);
        return model;
    }

    @Override
    protected String buildLabel(DataField field) {
        return concatSubfields(field, "abcd", " ");
    }
}
