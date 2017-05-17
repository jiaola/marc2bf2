package io.lold.marc2bf2.converters.field010to048;

import io.lold.marc2bf2.converters.InstanceIdConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field022Converter extends InstanceIdConverter {
    public Field022Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        List<Subfield> subfields = df.getSubfields('l');
        for (Subfield sf: subfields) {
            Resource resource = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.IssnL)
                    .addProperty(RDF.value, sf.getData());
            addSubfield2(df, resource);
            work.addProperty(BIB_FRAME.identifiedBy, resource);
        }

        subfields = df.getSubfields('m');
        for (Subfield sf: subfields) {
            Resource resource = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.IssnL)
                    .addProperty(RDF.value, sf.getData())
                    .addProperty(BIB_FRAME.status, model.createResource()
                            .addProperty(RDF.type, BIB_FRAME.Status)
                            .addProperty(RDFS.label, "canceled"));
            addSubfield2(df, resource);
            work.addProperty(BIB_FRAME.identifiedBy, resource);
        }

        Resource instance = ModelUtils.getInstance(model, record);
        List<Resource> resources = convert(field, BIB_FRAME.Issn, "canceled");
        for (Resource resource: resources) {
            addSubfield2(df, resource);
            instance.addProperty(BIB_FRAME.identifiedBy, resource);
        }
        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "022".equals(field.getTag());
    }
}
