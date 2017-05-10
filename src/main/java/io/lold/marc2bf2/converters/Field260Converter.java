package io.lold.marc2bf2.converters;

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

import java.text.Format;

public class Field260Converter extends NameTitleFieldConverter {
    public Field260Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("260")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        String lang = RecordUtils.getXmlLang(df, record);

        if (!df.getSubfields("abc").isEmpty()) {
            Resource resource = buildProvisionActivity(df, lang);
            if (df.getIndicator1() == '3') {
                resource.addProperty(BIB_FRAME.status, createLabeledResource(BIB_FRAME.Status, "current"));
            }
            addSubfield3(df, resource);
            String statement = concatSubfields(df, "abc", " ");
            instance.addProperty(BIB_FRAME.provisionActivity, resource)
                    .addProperty(BIB_FRAME.provisionActivityStatement, createLiteral(lang, statement));
        }

        if (!df.getSubfields("efg").isEmpty()) {
            Resource pa = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.ProvisionActivity)
                    .addProperty(RDF.type, BIB_FRAME.Manufacture);
            addSubfield3(df, pa);
            for (Subfield sf: df.getSubfields('e')) {
                String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
                pa.addProperty(BIB_FRAME.place, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Place)
                        .addProperty(RDFS.label, createLiteral(lang, value)));
            }
            for (Subfield sf: df.getSubfields('f')) {
                String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
                pa.addProperty(BIB_FRAME.agent, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Agent)
                        .addProperty(RDFS.label, createLiteral(lang, value)));
            }
            for (Subfield sf: df.getSubfields('g')) {
                String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
                pa.addProperty(BIB_FRAME.date, createLiteral(lang, value));
            }
            instance.addProperty(BIB_FRAME.provisionActivity, pa);
        }
        for (Subfield sf: df.getSubfields('d')) {
            instance.addProperty(BIB_FRAME.identifiedBy, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.PublisherNumber)
                    .addProperty(RDF.value, sf.getData()));
        }

        return model;
    }

    public Resource buildProvisionActivity(DataField field, String lang) {
        Resource pa = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.ProvisionActivity)
                .addProperty(RDF.type, BIB_FRAME.Publication);
        for (Subfield sf: field.getSubfields('a')) {
            String value = FormatUtils.chopBrackets(FormatUtils.chopPunctuation(sf.getData()));
            pa.addProperty(BIB_FRAME.place, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Place)
                    .addProperty(RDFS.label, createLiteral(lang, value)));
        }
        for (Subfield sf: field.getSubfields('b')) {
            String value = FormatUtils.chopBrackets(FormatUtils.chopPunctuation(sf.getData()));
            pa.addProperty(BIB_FRAME.agent, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Agent)
                    .addProperty(RDFS.label, createLiteral(lang, value)));
        }
        for (Subfield sf: field.getSubfields('c')) {
            String value = FormatUtils.chopBrackets(FormatUtils.chopPunctuation(sf.getData()));
            pa.addProperty(BIB_FRAME.date, createLiteral(lang, value));
        }
        return pa;

    }
}
