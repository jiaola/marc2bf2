package io.lold.marc2bf2.converters.field001to007;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field003Converter extends FieldConverter {
    public Field003Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        Resource resource = model.createResource().
                addProperty(RDF.type, BIB_FRAME.Source).
                addProperty(BIB_FRAME.code, ((ControlField)field).getData());
        Resource amd = ModelUtils.getAdminMatadata(model, record);
        amd.addProperty(BIB_FRAME.source, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "003".equals(field.getTag());
    }
}
