package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.utils.ModelUtils;
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

public class Field651Converter extends Field648Converter {
    public Field651Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);

        String uri = null;
        List<Subfield> sf0orws = df.getSubfields("0w");
        for (Subfield sf0orw: sf0orws) {
            uri = getUriFromSubfield0OrW(sf0orw);
            if (StringUtils.isNotBlank(uri)) {
                sf0orws.remove(sf0orw);
                break;
            }
        }
        if (StringUtils.isBlank(uri)) {
            uri = ModelUtils.buildUri(record, "Topic", getTag(df), fieldIndex);
        }

        Resource madsClass = df.getSubfields("bcd").isEmpty() ?
                MADS_RDF.Geographic : MADS_RDF.ComplexSubject;
        Resource resource = model.createResource(uri)
                .addProperty(RDF.type, BIB_FRAME.Place)
                .addProperty(RDF.type, madsClass);

        String label = concatSubfields(df, "abvxyz", "--");
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(RDFS.label, createLiteral(label, lang));
            resource.addProperty(MADS_RDF.authoritativeLabel, createLiteral(label, lang));
        }
        for (String scheme: ModelUtils.getMADSScheme(df.getIndicator2())) {
            resource.addProperty(MADS_RDF.isMemberofMADSScheme, model.createResource(scheme));
        }
        if (MADS_RDF.ComplexSubject.equals(madsClass)) {
            addComponentList(df, lang, resource, "abvxyz");
        }
        for (Subfield sf: df.getSubfields('g')) {
            resource.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, sf.getData(), lang));
        }
        for (Resource role: contributionRelationship(df.getSubfields('e'), lang, work)) {
            resource.addProperty(BIB_FRAME_LC.relationship, role.addProperty(BIB_FRAME.relatedTo, work));
        }
        addSubfield0AndW(sf0orws, resource);
        addSourceCode(df, resource);
        work.addProperty(BIB_FRAME.subject, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "651".equals(field.getTag());
    }

    @Override
    protected Resource getComponentType(char code) {
        switch (code) {
            case 'a':
            case 'b': return MADS_RDF.Geographic;
            default: return super.getComponentType(code);
        }
    }
}
