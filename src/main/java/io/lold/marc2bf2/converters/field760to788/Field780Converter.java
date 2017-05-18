package io.lold.marc2bf2.converters.field760to788;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field780Converter extends Field760Converter {
    public Field780Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;

        Resource resource = createWork(df);
        resource.addProperty(BIB_FRAME.hasInstance, createInstance(df).addProperty(BIB_FRAME.instanceOf, resource));

        Property property;
        switch (df.getIndicator2()) {
            case '0': property = BIB_FRAME.continues; break;
            case '1': property = BIB_FRAME.continuesInPart; break;
            case '4': property = BIB_FRAME.mergerOf; break;
            case '5':
            case '6': property = BIB_FRAME.absorbed; break;
            case '7': property = BIB_FRAME.separatedFrom; break;
            default: property = BIB_FRAME.precededBy;
        }
        ModelUtils.getWork(model, record).addProperty(property, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "780".equals(field.getTag());
    }

}
