package io.lold.marc2bf2.converters.field5XX;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field583Converter extends Field541Converter {
    public Field583Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public boolean checkField(VariableField field) {
        return "583".equals(field.getTag());
    }

    protected Resource buildItem(DataField field) {
        String itemUri = ModelUtils.buildUri(record, "Item", getTag(field), fieldIndex);
        Resource item = model.createResource(itemUri).addProperty(RDF.type, BIB_FRAME.Item);

        Resource note = model.createResource().addProperty(RDF.type, BIB_FRAME.Note)
                .addProperty(BIB_FRAME.noteType, "action");
        for (Subfield sf: field.getSubfields("ah")) {
            note.addProperty(RDFS.label, sf.getData());
        }
        for (Subfield sf: field.getSubfields('c')) {
            note.addProperty(BIB_FRAME.date, sf.getData());
        }
        for (Subfield sf: field.getSubfields('k')) {
            note.addProperty(BIB_FRAME.agent, createLabeledResource(BIB_FRAME.Agent, sf.getData()));
        }
        for (Subfield sf: field.getSubfields('l')) {
            note.addProperty(BIB_FRAME.status, createLabeledResource(BIB_FRAME.Status, sf.getData()));
        }
        for (Subfield sf: field.getSubfields('z')) {
            note.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, sf.getData()));
        }
        addSubfieldU(field, note);
        addSubfield2(field, note);
        item.addProperty(BIB_FRAME.note, note);
        addSubfield3(field, item);
        addSubfield5(field, item);
        return item;
    }

}
