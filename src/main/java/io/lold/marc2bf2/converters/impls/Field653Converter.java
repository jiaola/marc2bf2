package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field653Converter extends Field648Converter {
    public Field653Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);


        Resource type;
        switch (df.getIndicator2()) {
            case '1': type = BIB_FRAME.Person; break;
            case '2': type = BIB_FRAME.Organization; break;
            case '3': type = BIB_FRAME.Meeting; break;
            case '4': type = BIB_FRAME.Temporal; break;
            case '5': type = BIB_FRAME.Place; break;
            case '6': type = BIB_FRAME.GenreForm; break;
            default: type = BIB_FRAME.Topic;
        }
        Property property = df.getIndicator2() == '6'? BIB_FRAME.genreForm : BIB_FRAME.subject;

        String label = concatSubfields(df, "a", "--");
        label = FormatUtils.chopPunctuation(label);
        work.addProperty(property, createLabeledResource(type, label, lang));
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "653".equals(getTag(field));
    }
}
