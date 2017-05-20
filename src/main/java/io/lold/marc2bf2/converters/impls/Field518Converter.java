package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field518Converter extends FieldConverter {
    public Field518Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);


        String label = concatSubfields(df, "adop", " ");
        if (StringUtils.isNotBlank(label)) {
            Resource resource = createLabeledResource(BIB_FRAME.Capture, label, lang);
            addSubfield3(df, resource);
            work.addProperty(BIB_FRAME.capture, resource);
        }
        return model;
    }


    @Override
    public boolean checkField(VariableField field) {
        return "518".equals(field.getTag());
    }
}
