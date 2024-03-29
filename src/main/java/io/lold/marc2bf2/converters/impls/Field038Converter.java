package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
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

public class Field038Converter extends FieldConverter {
    public Field038Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource amd = ModelUtils.getAdminMatadata(model, record);
        DataField df = (DataField) field;
        List<Subfield> subfields = df.getSubfields('a');
        for (Subfield sf: subfields) {
            Resource ml = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME_LC.MetadataLicensor)
                    .addProperty(RDFS.label, sf.getData());
            amd.addProperty(BIB_FRAME_LC.metadataLicensor, ml);
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "038".equals(getTag(field));
    }
}
