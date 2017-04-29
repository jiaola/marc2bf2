package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
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
    public Model convert(VariableField field) {
        if (!field.getTag().equals("245")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);

        String lang = RecordUtils.getXmlLang(df, record);
        String label = record.getVariableFields(new String[]{"130", "240"}).isEmpty() ?
                concatSubfields(df, "abfgknps", " ") : null;
        if (StringUtils.isNotBlank(label) && "245".equals(field.getTag())) {
            work.addProperty(RDFS.label, createLiteral(lang, label));
        }

        work.addProperty(BIB_FRAME.title, buildTitle(df, lang, label));
        for (Subfield sf: df.getSubfields("fg")) {
            work.addProperty(BIB_FRAME.originDate, createLiteral(lang, sf.getData()));
        }
        for (Subfield sf: df.getSubfields('h')) {
            String value = FormatUtils.chopPunctuation(FormatUtils.chopBrackets(FormatUtils.chopPunctuation(sf.getData())));
            work.addProperty(BIB_FRAME.genreForm, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.GenreForm)
                    .addProperty(RDFS.label, createLiteral(lang, value)));
        }
        for (Subfield sf: df.getSubfields('s')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            work.addProperty(BIB_FRAME.version, createLiteral(lang, value));
        }

        Resource instance = ModelUtils.getInstance(model, record);
        label = concatSubfields(df, "abfgknps", " ");
        if (StringUtils.isNotBlank(label) && "245".equals(field.getTag())) {
            instance.addProperty(RDFS.label, createLiteral(lang, label));
        }
        instance.addProperty(BIB_FRAME.title, buildTitle(df, lang, label));
        for (Subfield sf: df.getSubfields('c')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.responsibilityStatement, createLiteral(lang, value));
        }
        for (Subfield sf: df.getSubfields('h')) {
            String value = FormatUtils.chopPunctuation(FormatUtils.chopBrackets(FormatUtils.chopPunctuation(sf.getData())));
            instance.addProperty(BIB_FRAME.genreForm, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.GenreForm)
                    .addProperty(RDFS.label, createLiteral(lang, value)));
        }

        return model;
    }

    protected Resource buildTitle(DataField df, String lang, String label) {
        Resource title = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Title);
        if (StringUtils.isNotBlank(label)) {
            title.addProperty(RDFS.label, createLiteral(lang, label))
                    .addProperty(BIB_FRAME_LC.titleSortKey, label);
        }
        for (Subfield sf: df.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            title.addProperty(BIB_FRAME.mainTitle, createLiteral(lang, value));
        }
        for (Subfield sf: df.getSubfields('b')) {
            String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
            title.addProperty(BIB_FRAME.subtitle, value);
        }
        for (Subfield sf: df.getSubfields('n')) {
            String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
            title.addProperty(BIB_FRAME.partNumber, value);
        }
        for (Subfield sf: df.getSubfields('p')) {
            String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
            title.addProperty(BIB_FRAME.partName, value);
        }
        return title;
    }
}
