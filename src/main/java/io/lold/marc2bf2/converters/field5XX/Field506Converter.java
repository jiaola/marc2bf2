package io.lold.marc2bf2.converters.field5XX;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field506Converter extends Field500Converter {
    public Field506Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        instance.addProperty(BIB_FRAME.usageAndAccessPolicy, buildResource(df, BIB_FRAME.UsageAndAccessPolicy));
        return model;
    }


    @Override
    public boolean checkField(VariableField field) {
        return "506".equals(field.getTag());
    }

    @Override
    protected String buildLabel(DataField field) {
        return concatSubfields(field, "abcdef", " ");
    }
}
