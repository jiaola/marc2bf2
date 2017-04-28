package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.ModelFactory;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field042Converter extends FieldConverter {
    public Field042Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("042")) {
            return model;
        }
        Resource amd = ModelUtils.getAdminMatadata(model, record);
        DataField df = (DataField) field;
        List<Subfield> subfields = df.getSubfields('a');
        for (Subfield sf: subfields) {
            Resource ml = model.createResource(ModelUtils.getUriWithNsPrefix("marcauthen", sf.getData()))
                    .addProperty(RDF.type, BIB_FRAME.DescriptionAuthentication);
            amd.addProperty(BIB_FRAME.descriptionAuthentication, ml);
        }
        return model;
    }

}
