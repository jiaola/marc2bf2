package io.lold.marc2bf2.converters.field5XX;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field546Converter extends FieldConverter {
    public Field546Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        Resource note = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Note);
        String lang = RecordUtils.getXmlLang(df, record);
        for (Subfield sf: df.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            note.addProperty(RDFS.label, createLiteral(value, lang));
        }

        for (Subfield sf: df.getSubfields('b')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            note.addProperty(BIB_FRAME.notation, createLabeledResource(BIB_FRAME.Notation, value, lang));
        }
        addSubfield3(df, note);

        work.addProperty(BIB_FRAME.language, model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Language)
                .addProperty(BIB_FRAME.note, note));
        return model;
    }


    @Override
    public boolean checkField(VariableField field) {
        return "546".equals(field.getTag());
    }

}
