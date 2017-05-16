package io.lold.marc2bf2.converters.field648to662;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import io.lold.marc2bf2.vocabulary.MADS_RDF;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.ArrayList;
import java.util.List;

public class Field650Converter extends Field648Converter {
    public Field650Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("650")) {
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
        if (StringUtils.isBlank(uri)) {
            uri = ModelUtils.buildUri(record, "Temporal", getTag(df), fieldIndex);
        }

        Resource madsClass = df.getSubfields("bcd").isEmpty() ?
                MADS_RDF.Topic : MADS_RDF.ComplexSubject;
        Resource resource = model.createResource(uri)
                .addProperty(RDF.type, BIB_FRAME.Topic)
                .addProperty(RDF.type, madsClass);

        String label = concatSubfields(df, "abcdvxyz", "--");
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(RDFS.label, createLiteral(label, lang));
            resource.addProperty(MADS_RDF.authoritativeLabel, createLiteral(label, lang));
        }
        for (String scheme: ModelUtils.getMADSScheme(df.getIndicator2())) {
            resource.addProperty(MADS_RDF.isMemberofMADSScheme, model.createResource(scheme));
        }
        if (MADS_RDF.ComplexSubject.equals(madsClass)) {
            List<Resource> list = new ArrayList<>();
            for (Subfield sf: df.getSubfields("abx")) {
                String value = FormatUtils.chopPunctuation(sf.getData());
                list.add(createComplexObject(MADS_RDF.Topic, MADS_RDF.authoritativeLabel, value, lang));
            }
            for (Subfield sf: df.getSubfields('v')) {
                String value = FormatUtils.chopPunctuation(sf.getData());
                list.add(createComplexObject(MADS_RDF.GenreForm, MADS_RDF.authoritativeLabel, value, lang));
            }
            for (Subfield sf: df.getSubfields("dy")) {
                String value = FormatUtils.chopPunctuation(sf.getData());
                list.add(createComplexObject(MADS_RDF.Temporal, MADS_RDF.authoritativeLabel, value, lang));
            }
            for (Subfield sf: df.getSubfields("cz")) {
                String value = FormatUtils.chopPunctuation(sf.getData());
                list.add(createComplexObject(MADS_RDF.Geographic, MADS_RDF.authoritativeLabel, value, lang));
            }
            resource.addProperty(MADS_RDF.componentList, model.createList(list.toArray(new RDFNode[list.size()])));
        }
        for (Subfield sf: df.getSubfields('g')) {
            resource.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, sf.getData(), lang));
        }
        for (Resource role: contributionRelationship(df.getSubfields('e'), lang, work)) {
            resource.addProperty(BIB_FRAME_LC.relationship, role.addProperty(BIB_FRAME.relatedTo, work));
        }
        addSubfield0(df, resource);
        addSourceCode(df, resource);
        work.addProperty(BIB_FRAME.subject, resource);
        return model;
    }
}
