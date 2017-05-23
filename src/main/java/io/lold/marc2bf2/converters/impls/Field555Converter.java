package io.lold.marc2bf2.converters.impls;

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
    protected Model process(VariableField field) {
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
    public boolean checkField(VariableField field) {
        return "555".equals(getTag(field));
    }

    @Override
    protected String buildLabel(DataField field) {
        return concatSubfields(field, "abcd", " ");
    }
}
