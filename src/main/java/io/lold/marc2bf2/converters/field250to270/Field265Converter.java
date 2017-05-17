package io.lold.marc2bf2.converters.field250to270;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field265Converter extends FieldConverter {
    public Field265Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        for (Subfield sf: df.getSubfields('a')) {
            instance.addProperty(BIB_FRAME.acquisitionSource, sf.getData());
        }

        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "265".equals(field.getTag());
    }
}
