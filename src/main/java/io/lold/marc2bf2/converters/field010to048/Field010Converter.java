package io.lold.marc2bf2.converters.field010to048;

import io.lold.marc2bf2.converters.InstanceIdConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field010Converter extends InstanceIdConverter {
    public Field010Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;

        Resource instance = ModelUtils.getInstance(model, record);
        List<Resource> resources = convert(field, BIB_FRAME.Lccn);
        for (Resource resource: resources) {
            addSubfield2(df, resource);
            instance.addProperty(BIB_FRAME.identifiedBy, resource);
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "010".equals(field.getTag());
    }
}
