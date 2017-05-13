package io.lold.marc2bf2.converters.field3XX;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
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

public class Field348Converter extends Field344Converter {
    public Field348Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("348")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        List<Subfield> sfs = df.getSubfields();
        for (int i = 0; i < sfs.size(); i++) {
            Subfield sf = sfs.get(i);
            if (sf.getCode() != 'a') continue;
            Resource resource = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.MusicFormat)
                    .addProperty(RDFS.label, sf.getData());

            Subfield sfb = RecordUtils.lookAhead(df, i, sfs.size(), 'b');
            if (sfb != null) {
                resource.addProperty(BIB_FRAME.code, sfb.getData());
            }
            Subfield sf0 = RecordUtils.lookAhead(df, i, sfs.size(), '0');
            if (sf0 != null) {
                resource.addProperty(BIB_FRAME.identifiedBy, SubfieldUtils.mapSubfield0(model, sf0.getData()));
            }
            addSubfield2(df, resource);
            addSubfield3(df, resource);
            instance.addProperty(BIB_FRAME.musicFormat, resource);
        }

        return model;
    }

}
