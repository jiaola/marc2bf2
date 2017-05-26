package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field100Converter extends NameTitleFieldConverter {
    public Field100Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        work.addProperty(BIB_FRAME.contribution, buildContribution(df));
        if (df.getSubfield('t') != null) {
            addUniformTitle(df, work);
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "100".equals(getTag(field));
    }
}
