package io.lold.marc2bf2.converters.field490_510_530to535;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field530Converter extends NameTitleFieldConverter {
    public Field530Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("530")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        Resource instance = ModelUtils.getInstance(model, record);

        Resource hasInstance = hasInstance(df).addProperty(BIB_FRAME.instanceOf, work)
                .addProperty(BIB_FRAME.otherPhysicalFormat, instance);
        work.addProperty(BIB_FRAME.hasInstance, hasInstance);
        instance.addProperty(BIB_FRAME.otherPhysicalFormat, hasInstance);

        return model;
    }

    protected Resource hasInstance(DataField field) {
        String instanceUri = ModelUtils.buildUri(record, "Instance", field.getTag(), fieldIndex);
        Resource instance = model.createResource(instanceUri)
                .addProperty(RDF.type, BIB_FRAME.Instance);
        VariableField f130 = record.getVariableField("130");
        String lang = RecordUtils.getXmlLang(field, record);
        if (f130 != null) {
            Resource title = buildUniformTitle((DataField) f130, titleLabel((DataField)f130), "130", lang);
            instance.addProperty(BIB_FRAME.title, title);
        } else {
            VariableField f240 = record.getVariableField("240");
            if (f240 != null) {
                Resource title = buildUniformTitle((DataField) f240, titleLabel((DataField) f240), "240", lang);
                instance.addProperty(BIB_FRAME.title, title);
            } else {
                VariableField f245 = record.getVariableField("245");
                if (f245 != null) {
                    String label = concatSubfields((DataField) f245, "abfgknps", " ");
                    Resource title = buildTitleFrom245((DataField) f245, lang, label);
                    instance.addProperty(BIB_FRAME.title, title);
                }
            }
        }

        for (Subfield sf: field.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, value, lang));
        }
        for (Subfield sf: field.getSubfields('b')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.acquisitionSource, createLiteral(value, lang));
        }
        for (Subfield sf: field.getSubfields('c')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.acquisitionTerms, createLiteral(value, lang));
        }
        for (Subfield sf: field.getSubfields('d')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.identifiedBy, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.StockNumber)
                    .addProperty(RDF.value, value));
        }
        for (Subfield sf: field.getSubfields('u')) {
            if (!IRIResolver.checkIRI(sf.getData())) {
                instance.addProperty(BIB_FRAME.hasItem, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Item)
                        .addProperty(BIB_FRAME.itemOf, instance)
                        .addProperty(BIB_FRAME.electronicLocator, model.createResource(sf.getData())));
            }
        }
        addSubfield3(field, instance);
        return instance;
    }

}
