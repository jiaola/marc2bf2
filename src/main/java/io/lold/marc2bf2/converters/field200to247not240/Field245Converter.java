package io.lold.marc2bf2.converters.field200to247not240;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field245Converter extends FieldConverter {
    public Field245Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);

        String label = record.getVariableFields(new String[]{"130", "240"}).isEmpty() ?
                concatSubfields(df, "abfgknps", " ") : null;
        if (StringUtils.isNotBlank(label) && "245".equals(field.getTag())) {
            work.addProperty(RDFS.label, createLiteral(label, lang));
        }

        work.addProperty(BIB_FRAME.title, buildTitleFrom245(df, lang, label));
        for (Subfield sf: df.getSubfields("fg")) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            work.addProperty(BIB_FRAME.originDate, createLiteral(value, lang));
        }
        for (Subfield sf: df.getSubfields('h')) {
            String value = FormatUtils.chopPunctuation(FormatUtils.chopBrackets(FormatUtils.chopPunctuation(sf.getData())));
            work.addProperty(BIB_FRAME.genreForm, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.GenreForm)
                    .addProperty(RDFS.label, createLiteral(value, lang)));
        }
        for (Subfield sf: df.getSubfields('s')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            work.addProperty(BIB_FRAME.version, createLiteral(value, lang));
        }

        Resource instance = ModelUtils.getInstance(model, record);
        label = concatSubfields(df, "abfgknps", " ");
        if (StringUtils.isNotBlank(label) && "245".equals(field.getTag())) {
            instance.addProperty(RDFS.label, createLiteral(label, lang));
        }
        instance.addProperty(BIB_FRAME.title, buildTitleFrom245(df, lang, label));
        for (Subfield sf: df.getSubfields('c')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.responsibilityStatement, createLiteral(value, lang));
        }
        for (Subfield sf: df.getSubfields('h')) {
            String value = FormatUtils.chopPunctuation(FormatUtils.chopBrackets(FormatUtils.chopPunctuation(sf.getData())));
            instance.addProperty(BIB_FRAME.genreForm, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.GenreForm)
                    .addProperty(RDFS.label, createLiteral(value, lang)));
        }

        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "245".equals(field.getTag());
    }

}
