package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
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
    protected Model process(VariableField field) throws Exception {
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

    @Override
    public boolean checkField(VariableField field) {
        return "042".equals(field.getTag());
    }

}
