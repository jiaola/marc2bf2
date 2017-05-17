package io.lold.marc2bf2.converters.field648to662;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
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

public class Field662Converter extends Field648Converter {
    public Field662Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("662")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        String lang = RecordUtils.getXmlLang(df, record);
        String uri = null;

        for (Subfield sf0orw: df.getSubfields("0w")) {
            uri = getUriFromSubfield0OrW(sf0orw);
            if (StringUtils.isNotBlank(uri)) {
                break;
            }
        }
        Resource resource = StringUtils.isBlank(uri) ?
                model.createResource() : model.createResource(uri);
        resource.addProperty(RDF.type, BIB_FRAME.Place)
                .addProperty(RDF.type, MADS_RDF.HierarchicalGeographic);

        String label = concatSubfields(df, "abcdfgh", "--");
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(RDFS.label, createLiteral(label, lang));
            resource.addProperty(RDFS.label, createLiteral(label, lang));
        }
        for (String scheme: ModelUtils.getMADSScheme(df.getIndicator2())) {
            resource.addProperty(MADS_RDF.isMemberofMADSScheme, model.createResource(scheme));
        }
        addComponentList(df, lang, resource, "abcdfgh");
        for (Subfield sf: df.getSubfields('g')) {
            resource.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, sf.getData(), lang));
        }
        for (Resource role: contributionRelationship(df.getSubfields('e'), lang, work)) {
            resource.addProperty(BIB_FRAME_LC.relationship, role.addProperty(BIB_FRAME.relatedTo, work));
        }
        addSubfield0(df, resource);
        addSubfield2(df, resource);
        work.addProperty(BIB_FRAME.subject, resource);
        return model;
    }

    @Override
    protected Resource getComponentType(char code) {
        switch (code) {
            case 'a': return MADS_RDF.Country;
            case 'b': return MADS_RDF.County;
            case 'c': return MADS_RDF.State;
            case 'd': return MADS_RDF.City;
            case 'f': return MADS_RDF.CitySection;
            case 'g': return MADS_RDF.Region;
            case 'h': return MADS_RDF.ExtraterrestrialArea;
            default: return null;
        }
    }
}
