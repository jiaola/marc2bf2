package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Field060Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field060Converter.class);

    public Field060Converter(Model model, Record record) throws Exception {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);

        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.ClassificationNlm);
        for (Subfield a: df.getSubfields('a')) {
            resource.addProperty(BIB_FRAME.classificationPortion, createLiteral(a));
        }
        for (Subfield b: df.getSubfields('b')) {
            resource.addProperty(BIB_FRAME.itemPortion, createLiteral(b));
        }
        if (df.getIndicator2() == '0') {
            resource.addProperty(BIB_FRAME.source, createLabeledResource(BIB_FRAME.Source, "National Library of Medicine"));
        }

        work.addProperty(BIB_FRAME.classification, resource);

        Resource instance = ModelUtils.getInstance(model, record);
        NodeIterator ni = model.listObjectsOfProperty(instance, BIB_FRAME.hasItem);
        int index = ni.toList().size() + 1;
        String itemUri = ModelUtils.buildUri(record, "Item", index);
        Resource item = model.createResource(itemUri)
                .addProperty(RDF.type, BIB_FRAME.Item)
                .addProperty(BIB_FRAME.heldBy,
                        createLabeledResource(BIB_FRAME.Agent, "National Library of Medicine"));

        instance.addProperty(BIB_FRAME.hasItem, item);
        item.addProperty(BIB_FRAME.itemOf, instance);

        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "060".equals(getTag(field));
    }
}
