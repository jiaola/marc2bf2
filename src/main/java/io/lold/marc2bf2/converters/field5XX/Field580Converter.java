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

public class Field580Converter extends NameTitleFieldConverter {
    public Field580Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        Resource note = model.createResource().addProperty(RDF.type, BIB_FRAME.Note);
        for (Subfield sf: df.getSubfields('a')) {
            note.addProperty(RDFS.label, sf.getData());
        }
        work.addProperty(BIB_FRAME.note, note);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "580".equals(field.getTag());
    }
}
