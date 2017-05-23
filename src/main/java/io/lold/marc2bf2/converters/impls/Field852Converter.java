package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field852Converter extends Field850Converter {
    public Field852Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "852".equals(getTag(field));
    }

    @Override
    protected Resource createItem(DataField field, Subfield sf, int index) {
        Resource item = super.createItem(field, sf, index);
        for (Subfield sfb: field.getSubfields('b')) {
            item.addProperty(BIB_FRAME.sublocation,
                    createLabeledResource(BIB_FRAME.Sublocation, sfb.getData()));
        }
        String address = concatSubfields(field, "en", ", ");
        if (StringUtils.isNotBlank(address)) {
            item.addProperty(BIB_FRAME.sublocation,
                    createLabeledResource(BIB_FRAME.Sublocation, address));
        }
        for (Subfield sfu: field.getSubfields('u')) {
            item.addProperty(BIB_FRAME.electronicLocator, model.createResource(sfu.getData()));
        }
        for (Subfield sfxz: field.getSubfields("xz")) {
            item.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, sfxz.getData()));
        }
        return item;
    }
}
