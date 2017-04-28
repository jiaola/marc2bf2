package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.ModelFactory;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field044Converter extends FieldConverter {
    public Field044Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!"044".equals(field.getTag())) {
            return model;
        }

        DataField df = (DataField) field;
        List<Subfield> sfs = df.getSubfields();
        for (int i = 0; i < sfs.size(); i++) {
            Subfield sf = sfs.get(i);
            Resource resource = null;
            if (sf.getCode() == 'a') {
                String uri = ModelUtils.getUriWithNsPrefix("countries", sf.getData());
                resource = model.createResource(uri);
            } else if (sf.getCode() == 'c') {
                resource = model.createResource()
                        .addProperty(BIB_FRAME.code, sf.getData())
                        .addProperty(BIB_FRAME.source, ModelUtils.createSource(model, "ISO 3166"));
            } else if (sf.getCode() == 'b') {
                resource = model.createResource()
                        .addProperty(BIB_FRAME.code, sf.getData());
                Subfield two = RecordUtils.lookAhead(df, i, 2, '2');
                if (two != null) {
                    resource.addProperty(BIB_FRAME.source, SubfieldUtils.mapSubfield2(model, two.getData()));
                }
            }
            if (resource != null) {
                resource.addProperty(RDF.type, BIB_FRAME.Place);
                ModelUtils.getInstance(model, record)
                        .addProperty(BIB_FRAME.place, resource);
            }
        }

        return model;
    }
}
