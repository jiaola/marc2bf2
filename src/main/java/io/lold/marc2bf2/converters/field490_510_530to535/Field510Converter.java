package io.lold.marc2bf2.converters.field490_510_530to535;

import io.lold.marc2bf2.converters.FieldConverter;
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

public class Field510Converter extends FieldConverter {
    public Field510Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Instance);

        for (Subfield sf: df.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData(), "[:,;/\\s]+$");
            resource.addProperty(BIB_FRAME.title, createLabeledResource(BIB_FRAME.Title, value, lang));
        }
        for (Subfield sf: df.getSubfields("bc")) {
            String value = FormatUtils.chopPunctuation(sf.getData(), "[:,;/\\s]+$");
            String noteType = sf.getCode() == 'b' ? "Coverage" : "Location";
            resource.addProperty(BIB_FRAME.note, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Note)
                    .addProperty(BIB_FRAME.noteType, noteType)
                    .addProperty(RDFS.label, createLiteral(value, lang)));
        }
        for (Subfield sf: df.getSubfields('x')) {
            String value = FormatUtils.chopPunctuation(sf.getData(), "[:,;/\\s]+$");
            resource.addProperty(BIB_FRAME.identifiedBy, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Issn)
                    .addProperty(RDF.value, value));
        }

        //TODO: Confirm the property is bf:indexOf
        instance.addProperty(BIB_FRAME.indexOf, resource);
        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "510".equals(field.getTag());
    }
}
