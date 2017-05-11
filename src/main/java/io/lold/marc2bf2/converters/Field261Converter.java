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

public class Field261Converter extends NameTitleFieldConverter {
    public Field261Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("261")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        String lang = RecordUtils.getXmlLang(df, record);

        Resource pa = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.ProvisionActivity)
                .addProperty(RDF.type, BIB_FRAME.Production);
        addSubfield3(df, pa);
        for (Subfield sf: df.getSubfields("ab")) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            pa.addProperty(BIB_FRAME.agent, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Agent)
                    .addProperty(RDFS.label, createLiteral(value, lang)));
        }
        for (Subfield sf: df.getSubfields('d')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            pa.addProperty(BIB_FRAME.date, createLiteral(value, lang));
        }
        for (Subfield sf: df.getSubfields('f')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            pa.addProperty(BIB_FRAME.place, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Place)
                    .addProperty(RDFS.label, createLiteral(value, lang)));
        }
        instance.addProperty(BIB_FRAME.provisionActivity, pa);
        if (!df.getSubfields("abdf").isEmpty()) {
            String statment = concatSubfields(df, "abdf", " ");
            instance.addProperty(BIB_FRAME.provisionActivityStatement, statment);
        }
        if (!df.getSubfields('e').isEmpty()) {
            Resource pa2 = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.ProvisionActivity)
                    .addProperty(RDF.type, BIB_FRAME.Manufacture);
            for (Subfield sf: df.getSubfields('e')) {
                String value = FormatUtils.chopPunctuation(sf.getData());
                pa2.addProperty(BIB_FRAME.agent, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Agent)
                        .addProperty(RDFS.label, createLiteral(value, lang)));
            }
            instance.addProperty(BIB_FRAME.provisionActivity, pa2);
        }
        return model;
    }

}
