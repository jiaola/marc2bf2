package io.lold.marc2bf2.converters.field3xx;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field306Converter extends FieldConverter {
    public Field306Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("306")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        String lang = RecordUtils.getXmlLang(df, record);
        for (Subfield sf: df.getSubfields('a')) {
            instance.addProperty(BIB_FRAME.duration, createLiteral(sf.getData(), lang));
        }

        return model;
    }
}