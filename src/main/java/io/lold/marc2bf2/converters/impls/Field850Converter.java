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

import java.util.List;

public class Field850Converter extends FieldConverter {
    public Field850Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        NodeIterator ni = model.listObjectsOfProperty(instance, BIB_FRAME.hasItem);
        int itemCount = ni.toList().size();

        List<Subfield> sfas = df.getSubfields('a');
        for (int i = 0; i < sfas.size(); i++) {
            Resource item = createItem(df, sfas.get(i), itemCount+i);
            instance.addProperty(BIB_FRAME.hasItem, item.addProperty(BIB_FRAME.itemOf, instance));
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "850".equals(getTag(field));
    }

    protected Resource createItem(DataField field, Subfield sf, int index) {
        String itemUri = ModelUtils.buildUri(record, "Item", index);
        Resource item = model.createResource(itemUri)
                .addProperty(RDF.type, BIB_FRAME.Item);

        String data = sf.getData();
        Resource agent;
        if (data.length() < 10) {
            String uri = ModelUtils.getUriWithNsPrefix("organizations", data);
            agent = model.createResource(uri).addProperty(RDF.type, BIB_FRAME.Agent);
        } else {
            agent = createLabeledResource(BIB_FRAME.Agent, data);
        }
        item.addProperty(BIB_FRAME.heldBy, agent);
        return item;
    }
}
