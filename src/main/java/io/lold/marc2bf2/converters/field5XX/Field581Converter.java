package io.lold.marc2bf2.converters.field5XX;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field581Converter extends Field500Converter {
    public Field581Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        Resource note = buildResource(df, BIB_FRAME.Note).addProperty(BIB_FRAME.noteType, "related material");
        for (Subfield sf: df.getSubfields('z')) {
            note.addProperty(BIB_FRAME.identifiedBy, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Isbn)
                    .addProperty(RDF.value, sf.getData()));
        }
        instance.addProperty(BIB_FRAME.note, note);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "581".equals(field.getTag());
    }
}
