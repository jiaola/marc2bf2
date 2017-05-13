package io.lold.marc2bf2.converters.field3XX;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field347Converter extends Field344Converter {
    public Field347Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("347")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        processSubfield(instance, df, 'a', BIB_FRAME.digitalCharacteristic, BIB_FRAME.FileType);
        processSubfield(instance, df, 'b', BIB_FRAME.digitalCharacteristic, BIB_FRAME.EncodingFormat);
        processSubfield(instance, df, 'c', BIB_FRAME.digitalCharacteristic, BIB_FRAME.FileSize);
        processSubfield(instance, df, 'd', BIB_FRAME.digitalCharacteristic, BIB_FRAME.Resolution);
        processSubfield(instance, df, 'e', BIB_FRAME.digitalCharacteristic, BIB_FRAME.RegionalEncoding);
        processSubfield(instance, df, 'f', BIB_FRAME.digitalCharacteristic, BIB_FRAME.EncodedBitrate);

        return model;
    }

}
