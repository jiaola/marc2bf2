package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field600Converter extends NameTitleFieldConverter {
    public Field600Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        Resource resource = !df.getSubfields('t').isEmpty() ? buildWork(df) : buildAgent(df);
        work.addProperty(BIB_FRAME.subject, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "600".equals(getTag(field));
    }
}
