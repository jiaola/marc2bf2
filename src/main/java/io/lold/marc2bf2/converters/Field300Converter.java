package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.FormatUtils;
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

public class Field300Converter extends FieldConverter {
    public Field300Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("300")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        String lang = RecordUtils.getXmlLang(df, record);

        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Extent);
        String extent = concatSubfields(df, "afg", " ");
        resource.addProperty(RDFS.label, createLiteral(lang, extent));
        addSubfield3(df, resource);
        instance.addProperty(BIB_FRAME.extent, resource);
        for (Subfield sf: df.getSubfields("be")) {
            Resource note = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Note);
            String noteType = sf.getCode() == 'b' ?
                    "Physical details" : "Accompanying materials";
            note.addProperty(BIB_FRAME.noteType, noteType);
            String value = FormatUtils.chopPunctuation(sf.getData(), "[\\+;,:/\\s]+$");
            note.addProperty(RDFS.label, createLiteral(lang, value));
            addSubfield3(df, note);
            instance.addProperty(BIB_FRAME.note, note);
        }
        for (Subfield sf: df.getSubfields('c')) {
            String value = FormatUtils.chopPunctuation(sf.getData(), "[\\+;,:/\\s]+$");
            instance.addProperty(BIB_FRAME.dimensions, createLiteral(lang, value));
        }

        return model;
    }
}
