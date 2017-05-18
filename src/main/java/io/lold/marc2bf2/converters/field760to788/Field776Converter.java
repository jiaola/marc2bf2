package io.lold.marc2bf2.converters.field760to788;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field776Converter extends Field760Converter {
    public Field776Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;

        Resource resource = createInstance(df)
                .addProperty(BIB_FRAME.instanceOf, ModelUtils.getWork(model, record));
        ModelUtils.getInstance(model, record)
                .addProperty(BIB_FRAME.otherPhysicalFormat, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "776".equals(field.getTag());
    }


    public Resource createInstance(DataField field) {
        String uri = null;
        List<Subfield> sf0orws = field.getSubfields("0w");
        for (Subfield sf0orw: sf0orws) {
            uri = getUriFromSubfield0OrW(sf0orw);
            if (StringUtils.isNotBlank(uri)) {
                sf0orws.remove(sf0orw);
                break;
            }
        }
        if (StringUtils.isBlank(uri)) {
            uri = ModelUtils.buildUri(record, "Instance", getTag(field), fieldIndex);
        }
        Resource resource = model.createResource(uri)
                .addProperty(RDF.type, BIB_FRAME.Instance);

        for (Subfield sf: field.getSubfields('a')) {
            resource.addProperty(BIB_FRAME.contribution, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME_LC.PrimaryContribution)
                    .addProperty(BIB_FRAME.agent, createLabeledResource(BIB_FRAME.Agent, sf.getData(), lang)));
        }
        for (Subfield sf: field.getSubfields('c')) {
            resource.addProperty(BIB_FRAME.title, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Title)
                    .addProperty(BIB_FRAME.qualifier, createLiteral(sf.getData(), lang)));
        }
        for (Subfield sf: field.getSubfields('e')) {
            String luri = ModelUtils.getUriWithNsPrefix("languages", sf.getData());
            resource.addProperty(BIB_FRAME.language, model.createResource(luri)
                    .addProperty(RDF.type, BIB_FRAME.Language));
        }
        for (Subfield sf: field.getSubfields('i')) {
            resource.addProperty(BIB_FRAME_LC.relationship, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME_LC.Relationship)
                    .addProperty(BIB_FRAME_LC.relation, createLabeledResource(BIB_FRAME_LC.Relation, sf.getData(), lang))
                    .addProperty(BIB_FRAME.relatedTo, ModelUtils.getInstance(model, record)));

        }
        for (Subfield sf: field.getSubfields('s')) {
            resource.addProperty(BIB_FRAME.title, createLabeledResource(BIB_FRAME.Title, sf.getData(), lang));
        }
        for (Subfield sf: field.getSubfields('v')) {
            resource.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Title, sf.getData(), lang));
        }
        for (Subfield sf: field.getSubfields('b')) {
            resource.addProperty(BIB_FRAME.editionStatement, createLiteral(sf.getData(), lang));
        }
        for (Subfield sf: field.getSubfields('d')) {
            resource.addProperty(BIB_FRAME.provisionActivityStatement, createLiteral(sf.getData(), lang));
        }
        for (Subfield sf: field.getSubfields('f')) {
            String curi = ModelUtils.getUriWithNsPrefix("countries", sf.getData());
            resource.addProperty(BIB_FRAME.provisionActivity, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.ProvisionActivity)
                    .addProperty(BIB_FRAME.place, model.createResource(curi)
                            .addProperty(RDF.type, BIB_FRAME.Place)));
        }
        for (Subfield sf: field.getSubfields('g')) {
            resource.addProperty(BIB_FRAME.part, createLiteral(sf.getData(), lang));
        }
        for (Subfield sf: field.getSubfields('h')) {
            resource.addProperty(BIB_FRAME.extent, createLabeledResource(BIB_FRAME.Extent, sf.getData(), lang));
        }
        for (Subfield sf: field.getSubfields('k')) {
            resource.addProperty(BIB_FRAME.seriesStatement, createLiteral(sf.getData(), lang));
        }
        for (Subfield sf: field.getSubfields("mn")) {
            resource.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, sf.getData(), lang));
        }
        for (Subfield sf: field.getSubfields('r')) {
            resource.addProperty(BIB_FRAME.identifiedBy, createIdentifier(BIB_FRAME.ReportNumber, sf.getData()));
        }
        for (Subfield sf: field.getSubfields('t')) {
            resource.addProperty(BIB_FRAME.title, createLabeledResource(BIB_FRAME.Title, sf.getData(), lang));
        }
        for (Subfield sf: field.getSubfields('u')) {
            resource.addProperty(BIB_FRAME.identifiedBy, createIdentifier(BIB_FRAME.Strn, sf.getData()));
        }
        for (Subfield sf: field.getSubfields('x')) {
            resource.addProperty(BIB_FRAME.identifiedBy, createIdentifier(BIB_FRAME.Issn, sf.getData()));
        }
        for (Subfield sf: field.getSubfields('y')) {
            resource.addProperty(BIB_FRAME.identifiedBy, createIdentifier(BIB_FRAME.Coden, sf.getData()));
        }
        for (Subfield sf: field.getSubfields('z')) {
            resource.addProperty(BIB_FRAME.identifiedBy, createIdentifier(BIB_FRAME.Isbn, sf.getData()));
        }
        addSubfield0AndW(sf0orws, resource);
        addSubfield3(field, resource);
        return resource;
    }

}
