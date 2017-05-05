package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field380Converter extends FieldConverter {
    public Field380Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("380")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);

        String lang = RecordUtils.getXmlLang(df, record);

        for (Subfield sf: df.getSubfields('a')) {
            Resource resource = createLabeledResource(BIB_FRAME.GenreForm, sf.getData(), lang);
            addSubfield2(df, resource);
            work.addProperty(BIB_FRAME.genreForm, resource);
        }
        return model;
    }
}
