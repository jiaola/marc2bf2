package io.lold.marc2bf2.converters.field760to788;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field787Converter extends Field760Converter {
    public Field787Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;

        Resource resource = createWork(df);
        resource.addProperty(BIB_FRAME.hasInstance, createInstance(df).addProperty(BIB_FRAME.instanceOf, resource));

        ModelUtils.getWork(model, record).addProperty(BIB_FRAME.relatedTo, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "787".equals(field.getTag());
    }

}
