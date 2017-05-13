package io.lold.marc2bf2.converters.field5XX;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field504Converter extends Field500Converter {
    public Field504Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("504")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        Resource note = buildNote(df).addProperty(BIB_FRAME.noteType, "bibliography");
        for (Subfield sf: df.getSubfields('b')) {
            note.addProperty(BIB_FRAME.count, sf.getData());
        }
        instance.addProperty(BIB_FRAME.note, note);
        return model;
    }
}
