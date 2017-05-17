package io.lold.marc2bf2.converters.field3XX;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field345Converter extends Field344Converter {
    public Field345Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        processSubfield(instance, df, 'a', BIB_FRAME.projectionCharacteristic, BIB_FRAME.PresentationFormat);
        processSubfield(instance, df, 'b', BIB_FRAME.projectionCharacteristic, BIB_FRAME.ProjectionSpeed);

        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "345".equals(field.getTag());
    }


}
