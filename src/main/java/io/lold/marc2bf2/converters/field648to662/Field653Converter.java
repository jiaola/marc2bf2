package io.lold.marc2bf2.converters.field648to662;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import io.lold.marc2bf2.vocabulary.MADS_RDF;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.ArrayList;
import java.util.List;

public class Field653Converter extends Field648Converter {
    public Field653Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("653")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        String lang = RecordUtils.getXmlLang(df, record);

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
}
