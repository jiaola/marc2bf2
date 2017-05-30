package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field508Converter extends FieldConverter {
    public Field508Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);


        for (Subfield sf: df.getSubfields('a')) {
            work.addProperty(BIB_FRAME.credits, createLiteral(sf.getData(), lang));
        }

        return model;
    }


    @Override
    public boolean checkField(VariableField field) {
        return "508".equals(getTag(field));
    }
}
