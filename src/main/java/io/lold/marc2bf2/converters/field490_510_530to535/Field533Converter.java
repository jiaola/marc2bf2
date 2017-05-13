package io.lold.marc2bf2.converters.field490_510_530to535;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import io.lold.marc2bf2.vocabulary.MADS_RDF;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;
import java.util.stream.Collectors;

public class Field533Converter extends Field530Converter {
    public Field533Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("533")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        Resource instance = ModelUtils.getInstance(model, record);

        Resource hasInstance = hasInstance(df).addProperty(BIB_FRAME.instanceOf, work)
                .addProperty(BIB_FRAME.reproductionOf, instance);
        work.addProperty(BIB_FRAME.hasInstance, hasInstance);
        instance.addProperty(BIB_FRAME.hasReproduction, hasInstance);

        return model;
    }

    @Override
    protected Resource hasInstance(DataField field) {
        String instanceUri = ModelUtils.buildUri(record, "Instance", field.getTag(), fieldIndex);
        Resource instance = model.createResource(instanceUri)
                .addProperty(RDF.type, BIB_FRAME.Instance);
        String lang = RecordUtils.getXmlLang(field, record);
        VariableField f245 = record.getVariableField("245");
        if (f245 != null) {
            String label = concatSubfields((DataField) f245, "abfgknps", " ");
            Resource title = buildTitleFrom245((DataField) f245, lang, label);
            instance.addProperty(BIB_FRAME.title, title);
        }

        for (Subfield sf: field.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.carrier, createLabeledResource(BIB_FRAME.Carrier, value, lang));
        }
        List<Subfield> sfbcds = field.getSubfields("bcd");
        if (!sfbcds.isEmpty()) {
            Resource activity = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.ProvisionActivity);
            for (Subfield sf: sfbcds) {
                String value = FormatUtils.chopPunctuation(sf.getData());
                if (sf.getCode() == 'b') {
                    activity.addProperty(BIB_FRAME.place, createLabeledResource(BIB_FRAME.Place, value, lang));
                } else if (sf.getCode() == 'c') {
                    activity.addProperty(BIB_FRAME.agent, createLabeledResource(BIB_FRAME.Agent, value, lang));
                } else {
                    activity.addProperty(BIB_FRAME.date, createLiteral(value, lang));
                }
            }
            instance.addProperty(BIB_FRAME.provisionActivity, activity);
        }

        for (Subfield sf: field.getSubfields('e')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            instance.addProperty(BIB_FRAME.extent, createLabeledResource(BIB_FRAME.Extent, value, lang));
        }
        for (Subfield sf: field.getSubfields('f')) {
            String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
            instance.addProperty(BIB_FRAME.seriesStatement, createLiteral(value, lang));
        }
        for (Subfield sf: field.getSubfields('n')) {
            instance.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, sf.getData(), lang));
        }
        for (Subfield sf3: field.getSubfields("3m")) {
            Resource appliesTo = SubfieldUtils.mapSubfield3(model, sf3.getData());
            instance.addProperty(BIB_FRAME_LC.appliesTo, appliesTo);
        }
        addSubfield5(field, instance);
        addItemFrom535(instance, '2');
        return instance;
    }

    protected void addItemFrom535(Resource instance, char ind1) {
        List<VariableField> fields = record.getVariableFields();
        if (fields.size() > fieldIndex + 1) {
            DataField field = (DataField) fields.get(fieldIndex+1);
            if (field.getIndicator1() == ind1 && "535".equals(getTag(field))) {
                Resource item = model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Item);
                List<Subfield> sfbcds = field.getSubfields("bcd");
                List<String> values = sfbcds.stream().
                        map(sf -> FormatUtils.chopPunctuation(sf.getData())).
                        collect(Collectors.toList());
                List<Subfield> sfabcs = field.getSubfields("abc");
                String lang = RecordUtils.getXmlLang(field, record);
                if (!sfabcs.isEmpty()) {
                    Resource agent = model.createResource()
                            .addProperty(RDF.type, BIB_FRAME.Agent);
                    for (Subfield sf: field.getSubfields('a')) {
                        String value = FormatUtils.chopPunctuation(sf.getData());
                        agent.addProperty(RDFS.label, createLiteral(value, lang));
                    }
                    if (!values.isEmpty()) {
                        agent.addProperty(BIB_FRAME.place, model.createResource()
                                .addProperty(RDF.type, BIB_FRAME.Place)
                                .addProperty(RDF.type, MADS_RDF.Address)
                                .addProperty(RDFS.label, StringUtils.join(values, "; ")));
                    }
                    item.addProperty(BIB_FRAME.heldBy, agent);
                }
                addSubfield3(field, item);
                item.addProperty(BIB_FRAME.itemOf, instance);
                instance.addProperty(BIB_FRAME.hasItem, item);
            }
        }
    }



}
