package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field500Converter extends FieldConverter {
    public Field500Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("500")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        Resource note = buildNote(df);
        instance.addProperty(BIB_FRAME.note, note);
        return model;
    }

    protected Resource buildNote(DataField field) {
        String lang = RecordUtils.getXmlLang(field, record);
        String label = concatSubfields(field, "a", " ");
        Resource note = createLabeledResource(BIB_FRAME.Note, label, lang);
        addSubfieldU(field, note);
        addSubfield3(field, note);
        addSubfield5(field, note);
        return note;
    }
}
