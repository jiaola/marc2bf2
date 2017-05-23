package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field338Converter extends Field337Converter {
    public Field338Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "338".equals(getTag(field));
    }

    @Override
    protected Resource getRDAResource(String tag) {
        return BIB_FRAME.Carrier;
    }

    @Override
    protected Property getRDAProperty(String tag) {
        return BIB_FRAME.carrier;
    }

    protected String getUriWithPrefix(String tag, String value) {
        return ModelUtils.getUriWithNsPrefix("carrier", value);
    }
}
