package io.lold.marc2bf2.converters.field1XX_6XX_7XX_8XX;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field1XXNameConverter extends NameTitleFieldConverter {
    public Field1XXNameConverter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource work = ModelUtils.getWork(model, record);
        work.addProperty(BIB_FRAME.contribution, buildContribution((DataField) field));
        addUniformTitle((DataField)field, work);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        String tag = field.getTag();
        return "100".equals(tag) || "110".equals(tag) || "111".equals(tag);
    }
}
