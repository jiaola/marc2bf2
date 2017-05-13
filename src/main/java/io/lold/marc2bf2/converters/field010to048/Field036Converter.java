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

public class Field036Converter extends InstanceIdConverter {
    public Field036Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!"036".equals(field.getTag())) {
            return model;
        }
        Resource instance = ModelUtils.getInstance(model, record);
        DataField df = (DataField) field;
        List<Resource> resources = convert(field, BIB_FRAME.StudyNumber);
        for (Resource resource: resources) {
            for (Resource source: convertSubfieldB(df)) {
                resource.addProperty(BIB_FRAME.source, source);
            }
            instance.addProperty(BIB_FRAME.identifiedBy, resource);
        }
        return model;
    }
}
