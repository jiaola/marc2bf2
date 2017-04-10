package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field001Converter extends FieldConverter {
    public Field001Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("001")) {
            return model;
        }
        Resource resource = model.createResource().
                addProperty(RDF.type, BIB_FRAME.Local).
                addProperty(RDF.value, ((ControlField)field).getData());
        Resource amd = ModelUtils.getAdminMatadata(model, record);
        amd.addProperty(BIB_FRAME.identifiedBy, resource);
        return model;
    }
}
