package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
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

public class Field502Converter extends FieldConverter {
    public Field502Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);


        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Dissertation);
        for (Subfield sf: df.getSubfields('a')) {
            resource.addProperty(RDFS.label, createLiteral(sf.getData(), lang));
        }
        for (Subfield sf: df.getSubfields('b')) {
            resource.addProperty(BIB_FRAME.degree, createLiteral(sf.getData(), lang));
        }
        for (Subfield sf: df.getSubfields('c')) {
            resource.addProperty(BIB_FRAME.grantingInstitution,
                    createLabeledResource(BIB_FRAME.Agent, sf.getData(), lang));
        }
        for (Subfield sf: df.getSubfields('d')) {
            resource.addProperty(BIB_FRAME.date, createLiteral(sf.getData(), lang));
        }
        for (Subfield sf: df.getSubfields('g')) {
            resource.addProperty(BIB_FRAME.note,
                    createLabeledResource(BIB_FRAME.Note, sf.getData(), lang));
        }
        for (Subfield sf: df.getSubfields('o')) {
            resource.addProperty(BIB_FRAME.identifiedBy, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.DissertationIdentifier)
                    .addProperty(RDF.value, createLiteral(sf.getData(), lang)));
        }
        work.addProperty(BIB_FRAME.dissertation, resource);
        return model;
    }


    @Override
    public boolean checkField(VariableField field) {
        return "502".equals(getTag(field));
    }
}
