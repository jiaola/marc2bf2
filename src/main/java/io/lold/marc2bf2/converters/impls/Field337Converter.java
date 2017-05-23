package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field337Converter extends Field336Converter {
    public Field337Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        List<Resource> list = buildRDATypes((DataField) field);
        Resource instance = ModelUtils.getInstance(model, record);
        for (Resource resource: list) {
            instance.addProperty(getRDAProperty(field.getTag()), resource);
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "337".equals(getTag(field));
    }

    @Override
    protected Resource getRDAResource(String tag) {
        return BIB_FRAME.Media;
    }

    @Override
    protected Property getRDAProperty(String tag) {
        return BIB_FRAME.media;
    }

    protected String getUriWithPrefix(String tag, String value) {
        return ModelUtils.getUriWithNsPrefix("mediaType", value);
    }
}
