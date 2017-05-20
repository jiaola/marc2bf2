package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Field070Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field070Converter.class);

    public Field070Converter(Model model, Record record) throws Exception {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);

        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Classification);
        for (Subfield a: df.getSubfields('a')) {
            resource.addProperty(BIB_FRAME.classificationPortion, createLiteral(a));
        }
        for (Subfield b: df.getSubfields('b')) {
            resource.addProperty(BIB_FRAME.itemPortion, createLiteral(b));
        }
        resource.addProperty(BIB_FRAME.source, createLabeledResource(BIB_FRAME.Source, "National Agricultural Library"));

        work.addProperty(BIB_FRAME.classification, resource);

        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "070".equals(field.getTag());
    }
}
