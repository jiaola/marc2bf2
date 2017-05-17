package io.lold.marc2bf2.converters.field490_510_530to535;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field490Converter extends FieldConverter {
    public Field490Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        for (Subfield sf: df.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData(), "[:,;/\\s]+$");
            instance.addProperty(BIB_FRAME.seriesStatement, value);
        }
        for (Subfield sf: df.getSubfields('v')) {
            String value = FormatUtils.chopPunctuation(sf.getData(), "[:,;/\\s]+$");
            instance.addProperty(BIB_FRAME.seriesEnumeration, value);
        }
        for (Subfield sf: df.getSubfields('x')) {
            String value = FormatUtils.chopPunctuation(sf.getData(), "[:,;/\\s]+$");
            instance.addProperty(BIB_FRAME.hasSeries, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Instance)
                    .addProperty(BIB_FRAME.identifiedBy, model.createResource()
                            .addProperty(RDF.type, BIB_FRAME.Issn)
                            .addProperty(RDF.value, value)));
        }
        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "490".equals(field.getTag());
    }
}
