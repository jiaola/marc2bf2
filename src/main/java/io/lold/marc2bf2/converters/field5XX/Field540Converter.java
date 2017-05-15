package io.lold.marc2bf2.converters.field5XX;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field540Converter extends Field500Converter {
    public Field540Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("540")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        Resource resource = buildResource(df, BIB_FRAME.UsePolicy);

        String lang = RecordUtils.getXmlLang(df, record);
        for (Subfield sf: df.getSubfields('c')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.source, createLabeledResource(BIB_FRAME.Source, value, lang));
        }
        for (Subfield sf: df.getSubfields('d')) {
            String value = "Authorized users: " + FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, value, lang));
        }

        instance.addProperty(BIB_FRAME.usageAndAccessPolicy, resource);
        return model;
    }

    @Override
    protected String buildLabel(DataField field) {
        return FormatUtils.chopPunctuation(super.buildLabel(field));
    }
}
