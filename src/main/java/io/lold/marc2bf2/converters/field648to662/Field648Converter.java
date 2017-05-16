package io.lold.marc2bf2.converters.field648to662;

import io.lold.marc2bf2.ModelFactory;
import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.MADS_RDF;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field648Converter extends FieldConverter {
    public Field648Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("648")) {
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

        Resource madsClass = df.getSubfields("vxyz").isEmpty() ?
                MADS_RDF.Temporal : MADS_RDF.ComplexSubject;
        Resource resource = model.createResource(uri)
                .addProperty(RDF.type, BIB_FRAME.Temporal)
                .addProperty(RDF.type, madsClass);

        String label = concatSubfields(df, "avxyz", "--");
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(RDFS.label, createLiteral(label, lang));
            resource.addProperty(MADS_RDF.authoritativeLabel, createLiteral(label, lang));
        }
        for (String scheme: ModelUtils.getMADSScheme(df.getIndicator2())) {
            resource.addProperty(MADS_RDF.isMemberofMADSScheme, model.createResource(scheme));
        }
        addMadsClass(df, lang, madsClass, resource);
        addSubfield0(df, resource);
        addSourceCode(df, resource);
        work.addProperty(BIB_FRAME.subject, resource);
        return model;
    }

    protected void addMadsClass(DataField df, String lang, Resource madsClass, Resource resource) {
        if (MADS_RDF.ComplexSubject.equals(madsClass)) {
            RDFList list = model.createList();
            for (Subfield sf: df.getSubfields("ay")) {
                String value = FormatUtils.chopPunctuation(sf.getData());
                list.add(createComplexObject(BIB_FRAME.Temporal, MADS_RDF.authoritativeLabel, value, lang));
            }
            for (Subfield sf: df.getSubfields('v')) {
                String value = FormatUtils.chopPunctuation(sf.getData());
                list.add(createComplexObject(MADS_RDF.GenreForm, MADS_RDF.authoritativeLabel, value, lang));
            }
            for (Subfield sf: df.getSubfields('x')) {
                String value = FormatUtils.chopPunctuation(sf.getData());
                list.add(createComplexObject(MADS_RDF.Topic, MADS_RDF.authoritativeLabel, value, lang));
            }
            for (Subfield sf: df.getSubfields('z')) {
                String value = FormatUtils.chopPunctuation(sf.getData());
                list.add(createComplexObject(MADS_RDF.Geographic, MADS_RDF.authoritativeLabel, value, lang));
            }
            resource.addProperty(MADS_RDF.componentList, list);
        }
    }

    protected void addSourceCode(DataField field, Resource resource) {
        if (field.getIndicator2() == '7') {
            for (Subfield sf: field.getSubfields('2')) {
                resource.addProperty(BIB_FRAME.source, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Source)
                        .addProperty(BIB_FRAME.code, sf.getData()));
            }
        } else {
            String sourceCode = ModelUtils.getSubjectThesaurusCode(field.getIndicator2());
            if (StringUtils.isNotBlank(sourceCode)) {
                resource.addProperty(BIB_FRAME.source, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Source)
                        .addProperty(BIB_FRAME.code, sourceCode));
            }
        }
    }


    protected Resource createComplexObject(Resource type, Property property, String value, String lang) {
        return model.createResource()
                .addProperty(RDF.type, type)
                .addProperty(property, createLiteral(value, lang));
    }


}
