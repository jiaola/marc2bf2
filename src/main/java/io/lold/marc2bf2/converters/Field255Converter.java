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

public class Field255Converter extends FieldConverter {
    public Field255Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("255")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);

        String lang = RecordUtils.getXmlLang(df, record);
        for (Subfield sf: df.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            work.addProperty(BIB_FRAME.scale, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Scale)
                    .addProperty(RDFS.label, createLiteral(lang, value)));
        }
        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Cartographic);
        for (Subfield sf: df.getSubfields('b')) {
            String value = FormatUtils.chopPunctuation(sf.getData(), ":,;\\/\\s");
            resource.addProperty(BIB_FRAME.projection, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Projection)
                    .addProperty(RDFS.label, createLiteral(lang, value)));
        }
        for (Subfield sf: df.getSubfields('c')) {
            String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
            resource.addProperty(BIB_FRAME.coordinates, createLiteral(lang, value));
        }
        for (Subfield sf: df.getSubfields('d')) {
            String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
            resource.addProperty(BIB_FRAME.ascensionAndDeclination, createLiteral(lang, value));
        }
        for (Subfield sf: df.getSubfields('e')) {
            String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
            resource.addProperty(BIB_FRAME.equinox, createLiteral(lang, value));
        }
        for (Subfield sf: df.getSubfields('f')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.outerGRing, createLiteral(lang, value));
        }
        for (Subfield sf: df.getSubfields('g')) {
            String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
            resource.addProperty(BIB_FRAME.exclusionGRing, createLiteral(lang, value));
        }

        work.addProperty(BIB_FRAME.cartographicAttributes, resource);

        return model;
    }
}
