package io.lold.marc2bf2.converters.field3XX;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field362Converter extends Field344Converter {
    public Field362Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        String lang = RecordUtils.getXmlLang(df, record);

        String first = null, last = null;
        Subfield sfa = df.getSubfield('a');
        if (sfa != null) {
            if (sfa.getData().contains("-")) {
                first = StringUtils.substringBefore(sfa.getData(), "-");
                last = StringUtils.substringAfter(sfa.getData(), "-");
                if (df.getIndicator1() == '0') {
                    instance.addProperty(BIB_FRAME.firstIssue, createLiteral(first, lang));
                    instance.addProperty(BIB_FRAME.lastIssue, createLiteral(last, lang));
                }
            }
            if (df.getIndicator1() != '0') {
                Resource resource = createLabeledResource(BIB_FRAME.Note, sfa.getData(), lang);
                resource.addProperty(BIB_FRAME.noteType, "Numbering");
                instance.addProperty(BIB_FRAME.note, resource);
            }
        }

        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "362".equals(field.getTag());
    }
}
