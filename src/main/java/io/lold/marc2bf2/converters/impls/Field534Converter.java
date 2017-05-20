package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field534Converter extends Field533Converter {
    public Field534Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        Resource instance = ModelUtils.getInstance(model, record);

        Resource hasInstance = hasInstance(df).addProperty(BIB_FRAME.instanceOf, work)
                .addProperty(BIB_FRAME.originalVersionOf, instance);
        work.addProperty(BIB_FRAME.hasInstance, hasInstance);
        instance.addProperty(BIB_FRAME.originalVersion, hasInstance);

        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "534".equals(field.getTag());
    }

    @Override
    protected Resource hasInstance(DataField field) {
        String instanceUri = ModelUtils.buildUri(record, "Instance", field.getTag(), fieldIndex);
        Resource instance = model.createResource(instanceUri)
                .addProperty(RDF.type, BIB_FRAME.Instance);
        String lang = RecordUtils.getXmlLang(field, record);

        if (field.getSubfield('t') != null) {
            instance.addProperty(BIB_FRAME.title, createLiteral(field.getSubfield('t').getData(), lang));
        }

        for (Subfield sf: field.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.contribution, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Contribution)
                    .addProperty(BIB_FRAME.agent, createLabeledResource(BIB_FRAME.Agent, value, lang)));
        }
        for (Subfield sf: field.getSubfields('b')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.editionStatement, createLiteral(value, lang));
        }
        for (Subfield sf: field.getSubfields('c')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.provisionActivityStatement, createLiteral(value, lang));
        }
        for (Subfield sf: field.getSubfields('e')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.extent, createLabeledResource(BIB_FRAME.Extent, value, lang));
        }
        for (Subfield sf: field.getSubfields('f')) {
            String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
            instance.addProperty(BIB_FRAME.seriesStatement, createLiteral(value, lang));
        }
        for (Subfield sf: field.getSubfields('k')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.title, createLabeledResource(BIB_FRAME.KeyTitle, value, lang));
        }
        for (Subfield sf: field.getSubfields("mn")) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, value, lang));
        }
        for (Subfield sf: field.getSubfields("x")) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.identifiedBy, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Issn)
                    .addProperty(RDF.value, value));
        }
        for (Subfield sf: field.getSubfields("z")) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.identifiedBy, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Isbn)
                    .addProperty(RDF.value, value));
        }
        for (Subfield sf3: field.getSubfields("3p")) {
            Resource appliesTo = SubfieldUtils.mapSubfield3(model, sf3.getData());
            instance.addProperty(BIB_FRAME_LC.appliesTo, appliesTo);
        }
        addItemFrom535(instance, '1');
        return instance;
    }

}
