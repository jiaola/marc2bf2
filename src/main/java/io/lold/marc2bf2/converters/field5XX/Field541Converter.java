package io.lold.marc2bf2.converters.field5XX;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field541Converter extends NameTitleFieldConverter {
    public Field541Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("541")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        Resource item = buildItem(df).addProperty(BIB_FRAME.itemOf, instance);
        instance.addProperty(BIB_FRAME.hasItem, item);
        return model;
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
