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

public class Field016Converter extends InstanceIdConverter {
    public Field016Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;

        Resource amd = ModelUtils.getAdminMatadata(model, record);
        List<Resource> resources = convert(field, BIB_FRAME.Local);
        for (Resource resource: resources) {
            if (df.getIndicator1() == ' ') {
                resource.addProperty(BIB_FRAME.source, createLabeledResource(BIB_FRAME.Source, "Library and Archives Canada"));
            } else {
                addSubfield2(df, resource);
            }
            amd.addProperty(BIB_FRAME.identifiedBy, resource);
        }

        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "016".equals(field.getTag());
    }
}
