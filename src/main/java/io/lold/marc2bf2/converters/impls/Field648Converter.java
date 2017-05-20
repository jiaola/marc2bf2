package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import io.lold.marc2bf2.vocabulary.MADS_RDF;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.ArrayList;
import java.util.List;

public class Field648Converter extends FieldConverter {
    public Field648Converter(Model model, Record record) {
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
        if (MADS_RDF.ComplexSubject.equals(madsClass)) {
            addComponentList(df, lang, resource, "avxyz");
        }
        addSubfield0AndW(sf0orws, resource);
        addSourceCode(df, resource);
        work.addProperty(BIB_FRAME.subject, resource);

        return model;
    }

    protected void addComponentList(DataField df, String lang, Resource resource, String fields) {
        List<Resource> list = new ArrayList<>();
        for (Subfield sf: df.getSubfields(fields)) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            Resource type = getComponentType(sf.getCode());
            if (type != null) {
                list.add(model.createResource()
                        .addProperty(RDF.type, type)
                        .addProperty(getComponentProperty(), createLiteral(value, lang)));
            }
        }
        resource.addProperty(MADS_RDF.componentList, model.createList(list.toArray(new RDFNode[list.size()])));
    }

    @Override
    public boolean checkField(VariableField field) {
        return "648".equals(field.getTag());
    }

    protected Property getComponentProperty() {
        return RDFS.label;
    }

    protected Resource getComponentType(char code) {
        switch (code) {
            case 'v': return MADS_RDF.GenreForm;
            case 'x': return MADS_RDF.Topic;
            case 'y': return MADS_RDF.Temporal;
            case 'z': return MADS_RDF.Geographic;
            case 'a': return MADS_RDF.Temporal;
            default: return null;
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
}
