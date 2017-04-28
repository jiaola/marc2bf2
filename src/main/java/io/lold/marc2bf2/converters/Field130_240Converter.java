package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.ModelUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field130_240Converter extends NameTitleFieldConverter {
    public Field130_240Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!"130".equals(field.getTag()) && !"240".equals(field.getTag())) {
            return model;
        }

        Resource work = ModelUtils.getWork(model, record);
        addUniformTitle((DataField)field, work);
        return model;
    }

}
