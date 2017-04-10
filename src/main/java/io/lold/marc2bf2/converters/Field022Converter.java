package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.SubfieldUtils;
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

public class Field022Converter extends FieldConverter {
    public Field022Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("022")) {
            return model;
        }
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        List<Subfield> subfields = df.getSubfields('l');
        for (Subfield sf: subfields) {
            Resource r = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.IssnL)
                    .addProperty(RDF.value, sf.getData());
            Subfield sf2 = df.getSubfield('2');
            Resource source = SubfieldUtils.mapSubfield2(model, sf2.getData());
            r.addProperty(BIB_FRAME.source, source);
            work.addProperty(BIB_FRAME.identifiedBy, r);
        }

        subfields = df.getSubfields('m');
        for (Subfield sf: subfields) {
            Resource r = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.IssnL)
                    .addProperty(RDF.value, sf.getData())
                    .addProperty(BIB_FRAME.status, model.createResource()
                            .addProperty(RDF.type, BIB_FRAME.Status)
                            .addProperty(RDFS.label, "canceled"));
            Subfield sf2 = df.getSubfield('2');
            Resource source = SubfieldUtils.mapSubfield2(model, sf2.getData());
            r.addProperty(BIB_FRAME.source, source);
            work.addProperty(BIB_FRAME.identifiedBy, r);
        }

        // TODO: Implement

        return model;
    }
}
