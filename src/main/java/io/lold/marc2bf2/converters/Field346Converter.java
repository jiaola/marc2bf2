package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field346Converter extends Field344Converter {
    public Field346Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("346")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        processSubfield(instance, df, 'a', BIB_FRAME.videoCharacteristic, BIB_FRAME.VideoFormat);
        processSubfield(instance, df, 'b', BIB_FRAME.videoCharacteristic, BIB_FRAME.BroadcastStandard);

        return model;
    }

}
