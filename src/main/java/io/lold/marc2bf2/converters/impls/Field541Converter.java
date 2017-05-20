package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field541Converter extends NameTitleFieldConverter {
    public Field541Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        Resource item = buildItem(df).addProperty(BIB_FRAME.itemOf, instance);
        instance.addProperty(BIB_FRAME.hasItem, item);
        return model;
    }


    @Override
    public boolean checkField(VariableField field) {
        return "541".equals(field.getTag());
    }

    protected Resource buildItem(DataField field) {
        String itemUri = ModelUtils.buildUri(record, "Item", getTag(field), fieldIndex);
        Resource item = model.createResource(itemUri).addProperty(RDF.type, BIB_FRAME.Item);
        String label = concatSubfields(field, "abcdefhno", " ");
        item.addProperty(BIB_FRAME.immediateAcquisition, createLabeledResource(BIB_FRAME.ImmediateAcquisition, label));
        addSubfield3(field, item);
        addSubfield5(field, item);
        return item;
    }

}
