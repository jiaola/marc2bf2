package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
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

public class Field310Converter extends FieldConverter {
    public Field310Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        if (!field.getTag().equals("310") && !field.getTag().equals("321")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);


        for (Subfield sf: df.getSubfields('a')) {
            Resource resource = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Frequency);
            String value = FormatUtils.chopPunctuation(sf.getData(), "[;,:/\\s]+$");
            resource.addProperty(RDFS.label, createLiteral(value, lang));
            for (Subfield sfb: df.getSubfields('b')) {
                resource.addProperty(BIB_FRAME.date, createLiteral(sfb.getData(), lang));
            }
            instance.addProperty(BIB_FRAME.frequency, resource);
        }

        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "310".equals(field.getTag());
    }
}