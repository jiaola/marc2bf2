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

import java.util.List;

public class Field040Converter extends FieldConverter {
    public Field040Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource amd = ModelUtils.getAdminMatadata(model, record);
        DataField df = (DataField) field;

        List<Subfield> subfields = df.getSubfields('a');
        for (Subfield sf: subfields) {
            amd.addProperty(BIB_FRAME.source, createSource(sf.getData()));
        }
        subfields = df.getSubfields('c');
        for (Subfield sf: subfields) {
            amd.addProperty(BIB_FRAME.source, createSource(sf.getData()));
        }
        subfields = df.getSubfields('b');
        for (Subfield sf: subfields) {
            String value = sf.getData();
            Resource lang;
            if (value.length() == 3) {
                String uri = "http://id.loc.gov/vocabulary/languages/" + sf.getData();
                lang = model.createResource(uri);
            } else {
                lang = model.createResource().addProperty(BIB_FRAME.code, value);
            }
            amd.addProperty(BIB_FRAME.descriptionLanguage, lang.addProperty(RDF.type, BIB_FRAME.Language));
        }
        subfields = df.getSubfields('d');
        for (Subfield sf: subfields) {
            amd.addProperty(BIB_FRAME.descriptionModifier, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Agent)
                    .addProperty(RDFS.label, sf.getData()));
        }
        subfields = df.getSubfields('e');
        for (Subfield sf: subfields) {
            String value = sf.getData();
            Resource dc;
            if (value.trim().contains(" ")) {
                dc = model.createResource().addProperty(RDFS.label, value);
            } else {
                String uri = "http://id.loc.gov/vocabulary/descriptionConventions/" + value.trim();
                dc = model.createResource(uri);
            }
            amd.addProperty(BIB_FRAME.descriptionConventions, dc.addProperty(RDF.type, BIB_FRAME.DescriptionConventions));
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "040".equals(getTag(field));
    }

    private Resource createSource(String value) {
        return model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Agent)
                .addProperty(RDF.type, BIB_FRAME.Source)
                .addProperty(RDFS.label, value);
    }

}
