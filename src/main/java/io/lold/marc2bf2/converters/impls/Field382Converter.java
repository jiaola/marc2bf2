package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field382Converter extends FieldConverter {
    public Field382Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);



        List<Subfield> subfields = df.getSubfields();

        Resource resource = null;
        for (int i = 0; i < subfields.size(); i++) {
            Subfield sf = subfields.get(i);
            if ("abdp".contains(String.valueOf(sf.getCode()))) {
                if (resource != null) {
                    work.addProperty(BIB_FRAME.musicMedium, resource);
                }
                resource = model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.MusicMedium);
                if (sf.getCode() == 'd') {
                    resource.addProperty(BIB_FRAME.status,
                            createLabeledResource(BIB_FRAME.Status, "doubling"));
                } else if (sf.getCode() == 'p') {
                    resource.addProperty(BIB_FRAME.status,
                            createLabeledResource(BIB_FRAME.Status, "alternative"));
                }
                resource.addProperty(RDFS.label, createLiteral(sf.getData(), lang));
            } else if (sf.getCode() == 'n' || sf.getCode() == 'e') {
                if (resource != null)
                    resource.addProperty(BIB_FRAME.count, sf.getData());
            } else if (sf.getCode() == 'v') {
                // TODO: Handles following rst
                if (resource != null) {
                    resource.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, sf.getData(), lang));
                }
            } else if (sf.getCode() == '2') {
                if (resource != null) {
                    resource.addProperty(BIB_FRAME.source, SubfieldUtils.mapSubfield2(model, sf.getData()));
                }
            } else if (sf.getCode() == '3') {
                if (resource != null) {
                    resource.addProperty(BIB_FRAME_LC.appliesTo, SubfieldUtils.mapSubfield3(model, sf.getData()));
                }
            } else if ("rst".contains(String.valueOf(sf.getCode()))) {
                if (resource != null) {
                    work.addProperty(BIB_FRAME.musicMedium, resource);
                }
                resource = model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.MusicMedium);
                String value = "";
                if (sf.getCode() == 'r') value = "Total performers alongside ensembles: ";
                else if (sf.getCode() == 's') value = "Total performers: ";
                else if (sf.getCode() == 't') value = "Total ensembles: ";
                value += sf.getData();
                resource.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, value, lang));
                addSubfield3(df, resource);
            }
        }
        if (resource != null) {
            work.addProperty(BIB_FRAME.musicMedium, resource);
        }

        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "382".equals(getTag(field));
    }
}
