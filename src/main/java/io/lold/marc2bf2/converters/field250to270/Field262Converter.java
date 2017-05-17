package io.lold.marc2bf2.converters.field250to270;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field262Converter extends Field260Converter {
    public Field262Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        String lang = RecordUtils.getXmlLang(df, record);

        if (!df.getSubfields("abc").isEmpty()) {
            Resource resource = buildProvisionActivity(df, lang);
            String statement = concatSubfields(df, "abc", " ");
            instance.addProperty(BIB_FRAME.provisionActivity, resource)
                    .addProperty(BIB_FRAME.provisionActivityStatement, createLiteral(statement, lang));

        }

        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "262".equals(field.getTag());
    }
}
