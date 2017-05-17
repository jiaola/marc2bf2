package io.lold.marc2bf2.converters.field250to270;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field264Converter extends Field260Converter {
    public Field264Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);


        if (!df.getSubfields("abc").isEmpty()) {
            String statement = concatSubfields(df, "abc", " ");
            if (df.getIndicator2() == '4') {
                instance.addProperty(BIB_FRAME.copyrightDate, createLiteral(statement, lang));
            } else {
                Resource resource = buildProvisionActivity(df, lang);
                if (df.getIndicator1() == '3') {
                    resource.addProperty(BIB_FRAME.status,createLabeledResource(BIB_FRAME.Status, "current"));
                }
                addSubfield3(df, resource);
                instance.addProperty(BIB_FRAME.provisionActivity, resource)
                        .addProperty(BIB_FRAME.provisionActivityStatement, createLiteral(statement, lang));

            }
        }

        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "264".equals(field.getTag());
    }
}
