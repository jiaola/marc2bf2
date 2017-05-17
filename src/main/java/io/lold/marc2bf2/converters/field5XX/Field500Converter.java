package io.lold.marc2bf2.converters.field5XX;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field500Converter extends FieldConverter {
    public Field500Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        Resource note = buildResource(df, BIB_FRAME.Note);
        instance.addProperty(BIB_FRAME.note, note);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "500".equals(field.getTag());
    }

    protected Resource buildResource(DataField field, Resource type) {
        String lang = RecordUtils.getXmlLang(field, record);
        String label = buildLabel(field);
        Resource note = createLabeledResource(type, label, lang);
        addSubfieldU(field, note);
        addSubfield3(field, note);
        addSubfield5(field, note);
        return note;
    }

    protected String buildLabel(DataField field) {
        return concatSubfields(field, "a", " ");
    }
}
