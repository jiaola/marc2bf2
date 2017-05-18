package io.lold.marc2bf2.converters.field760to788;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field785Converter extends Field760Converter {
    public Field785Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;

        Resource resource = createWork(df);
        resource.addProperty(BIB_FRAME.hasInstance, createInstance(df).addProperty(BIB_FRAME.instanceOf, resource));

        Property property;
        switch (df.getIndicator2()) {
            case '0':
            case '8': property = BIB_FRAME.continuedBy; break;
            case '1': property = BIB_FRAME.continuedInPartBy; break;
            case '4':
            case '5': property = BIB_FRAME.absorbedBy; break;
            case '6': property = BIB_FRAME.splitInto; break;
            case '7': property = BIB_FRAME.mergedToForm; break;
            default: property = BIB_FRAME.succeededBy;
        }
        ModelUtils.getWork(model, record).addProperty(property, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "785".equals(field.getTag());
    }

}
