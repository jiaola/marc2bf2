package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field383Converter extends FieldConverter {
    public Field383Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);

        for (Subfield sf: df.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            work.addProperty(BIB_FRAME.musicSerialNumber, value);
        }
        for (Subfield sf: df.getSubfields('b')) {
            work.addProperty(BIB_FRAME.musicOpusNumber, sf.getData());
        }
        for (Subfield sf: df.getSubfields("cd")) {
            work.addProperty(BIB_FRAME.musicThematicNumber, sf.getData());
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "383".equals(getTag(field));
    }
}
