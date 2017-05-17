package io.lold.marc2bf2.converters.field5XX;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field511Converter extends FieldConverter {
    public Field511Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        String lang = RecordUtils.getXmlLang(df, record);

        for (Subfield sf: df.getSubfields('a')) {
            String value = sf.getData();
            if (df.getIndicator1() == '1') {
                value = "Cast: " + value;
            }
            work.addProperty(BIB_FRAME.credits, createLiteral(value, lang));
        }

        return model;
    }


    @Override
    public boolean checkField(VariableField field) {
        return "511".equals(field.getTag());
    }
}
