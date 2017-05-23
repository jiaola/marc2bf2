package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.InstanceIdConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field074Converter extends InstanceIdConverter {
    public Field074Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource instance = ModelUtils.getInstance(model, record);
        List<Resource> resources = convert(field, BIB_FRAME.Identifier);
        for (Resource resource: resources) {
            resource.addProperty(BIB_FRAME.source, createLabeledResource(BIB_FRAME.Source,"US GPO"));
            instance.addProperty(BIB_FRAME.identifiedBy, resource);
        }
        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "074".equals(getTag(field));
    }
}
