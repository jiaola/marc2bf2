package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field561Converter extends Field541Converter {
    public Field561Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "561".equals(field.getTag());
    }

    protected Resource buildItem(DataField field) {
        String itemUri = ModelUtils.buildUri(record, "Item", getTag(field), fieldIndex);
        Resource item = model.createResource(itemUri).addProperty(RDF.type, BIB_FRAME.Item);
        for (Subfield sf: field.getSubfields('a')) {
            item.addProperty(BIB_FRAME.custodialHistory, sf.getData());
        }
        addSubfield3(field, item);
        addSubfield5(field, item);
        return item;
    }

}
