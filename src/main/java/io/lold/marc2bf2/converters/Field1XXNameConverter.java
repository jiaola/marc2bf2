package io.lold.marc2bf2.converters;

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
    public Model convert(VariableField field) throws Exception {
        if (!"100".equals(field.getTag()) && !"110".equals(field.getTag()) && !"111".equals(field.getTag())) {
            return model;
        }

        Resource work = ModelUtils.getWork(model, record);
        work.addProperty(BIB_FRAME.contribution, buildContribution((DataField) field));
        addUniformTitle((DataField)field, work);
        return model;
    }

}