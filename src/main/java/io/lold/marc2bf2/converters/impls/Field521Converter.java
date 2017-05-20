package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field521Converter extends FieldConverter {
    public Field521Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);


        String note = null;
        if (df.getIndicator1() == '0') {
            note = "reading grade level";
        } else if (df.getIndicator1() == '1') {
            note = "interest age level";
        } else if (df.getIndicator1() == '2') {
            note = "interest grade level";
        }
        for (Subfield sf: df.getSubfields('a')) {
            Resource resource = createLabeledResource(BIB_FRAME.IntendedAudience, sf.getData(), lang);
            if (StringUtils.isNotBlank(note)) {
                resource.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, note, lang));
            }
            for (Subfield sfb: df.getSubfields('b')) {
                resource.addProperty(BIB_FRAME.source, SubfieldUtils.mapSubfield2(model, sfb.getData()));
            }
            addSubfield3(df, resource);
            instance.addProperty(BIB_FRAME.intendedAudience, resource);
        }

        return model;
    }


    @Override
    public boolean checkField(VariableField field) {
        return "521".equals(field.getTag());
    }
}
