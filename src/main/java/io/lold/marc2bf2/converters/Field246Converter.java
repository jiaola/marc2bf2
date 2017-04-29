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

public class Field246Converter extends FieldConverter {
    public Field246Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("246")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Title)
                .addProperty(RDF.type, BIB_FRAME.VariantTitle);
        switch (df.getIndicator2()) {
            case '0': resource.addProperty(BIB_FRAME.variantType, "portion"); break;
            case '1': resource.addProperty(RDF.type, BIB_FRAME.ParallelTitle); break;
            case '2': resource.addProperty(BIB_FRAME.variantType, "distinctive"); break;
            case '4': resource.addProperty(BIB_FRAME.variantType, "cover"); break;
            case '5': resource.addProperty(BIB_FRAME.variantType, "added title page"); break;
            case '6': resource.addProperty(BIB_FRAME.variantType, "caption"); break;
            case '7': resource.addProperty(BIB_FRAME.variantType, "running"); break;
            case '8': resource.addProperty(BIB_FRAME.variantType, "spine"); break;
        }
        String label = concatSubfields(df, "abgnp", " ");
        String lang = RecordUtils.getXmlLang(df, record);
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(RDFS.label, createLiteral(lang, label))
                    .addProperty(BIB_FRAME_LC.titleSortKey, label);
        }
        for (Subfield sf: df.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.mainTitle, createLiteral(lang, value));
        }
        for (Subfield sf: df.getSubfields('b')) {
            String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
            resource.addProperty(BIB_FRAME.subtitle, value);
        }
        for (Subfield sf: df.getSubfields('f')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.date, createLiteral(lang, value));
        }
        for (Subfield sf: df.getSubfields('n')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.partNumber, createLiteral(lang, value));
        }
        for (Subfield sf: df.getSubfields('p')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.partName, createLiteral(lang, value));
        }
        addSubfield5(df, resource);
        instance.addProperty(BIB_FRAME.title, resource);
        return model;
    }
}
