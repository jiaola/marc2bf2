package io.lold.marc2bf2.converters.field1XX_6XX_7XX_8XX;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field6XXNameConverter extends NameTitleFieldConverter {
    public Field6XXNameConverter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!"600".equals(field.getTag()) && !"610".equals(field.getTag()) && !"611".equals(field.getTag())) {
            return model;
        }

        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        Resource resource = !df.getSubfields('t').isEmpty() ? buildWork(df) : buildAgent(df);
        work.addProperty(BIB_FRAME.subject, resource);
        return model;
    }

}
