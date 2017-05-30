package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field250Converter extends FieldConverter {
    public Field250Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);


        String statment = FormatUtils.chopPunctuation(concatSubfields(df, "ab", " "));
        instance.addProperty(BIB_FRAME.editionStatement, createLiteral(statment, lang));

        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "250".equals(getTag(field));
    }
}
