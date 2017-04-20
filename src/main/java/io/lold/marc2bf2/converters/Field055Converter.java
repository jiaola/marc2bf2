package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.ModelFactory;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class Field055Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field055Converter.class);

    public Field055Converter(Model model, Record record) throws Exception {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("055")) {
            return model;
        }

        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        String lang = RecordUtils.getXmlLang(df, record);

        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.ClassificationLcc);
        for (Subfield a: df.getSubfields('a')) {
            resource.addProperty(BIB_FRAME.classificationPortion, createLiteral(lang, a));
        }
        for (Subfield b: df.getSubfields('b')) {
            resource.addProperty(BIB_FRAME.itemPortion, createLiteral(lang, b));
        }
        if (df.getIndicator2() == '0' || df.getIndicator2() == '1' || df.getIndicator2() == '2') {
            resource.addProperty(BIB_FRAME.source, ModelUtils.createSource(model, "Library and Archives Canada"));
        }

        work.addProperty(BIB_FRAME.classification, resource);

        return model;
    }
}
