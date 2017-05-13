package io.lold.marc2bf2.converters.field3xx;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field351Converter extends FieldConverter {
    public Field351Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("351")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);

        String lang = RecordUtils.getXmlLang(df, record);

        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Arrangement);
        addSubfield3(df, resource);
        for (Subfield sf: df.getSubfields('c')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.hierarchicalLevel, createLiteral(value, lang));
        }

        for (Subfield sf: df.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.organization, createLiteral(value, lang));
        }

        for (Subfield sf: df.getSubfields('b')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.pattern, createLiteral(value, lang));
        }

        work.addProperty(BIB_FRAME.arrangement, resource);

        return model;
    }
}
