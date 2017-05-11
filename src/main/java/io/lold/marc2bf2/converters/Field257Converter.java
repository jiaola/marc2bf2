package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field257Converter extends FieldConverter {
    public Field257Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("257")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        String lang = RecordUtils.getXmlLang(df, record);
        for (Subfield sf: df.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            Resource place = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Place)
                    .addProperty(RDFS.label, createLiteral(sf.getData(), lang));
            addSubfield2(df, place);
            instance.addProperty(BIB_FRAME.provisionActivity, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Production)
                    .addProperty(BIB_FRAME.place, place));
        }

        return model;
    }
}
