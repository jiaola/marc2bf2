package io.lold.marc2bf2.converters.field648to662;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
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

public class Field656Converter extends Field648Converter {
    public Field656Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("656")) {
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
        resource.addProperty(RDF.type, BIB_FRAME.Topic)
                .addProperty(RDF.type, MADS_RDF.ComplexSubject);

        String label = concatSubfields(df, "az", "--");
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(RDFS.label, createLiteral(label, lang));
            resource.addProperty(RDFS.label, createLiteral(label, lang));
        }
        for (String scheme: ModelUtils.getMADSScheme(df.getIndicator2())) {
            resource.addProperty(MADS_RDF.isMemberofMADSScheme, model.createResource(scheme));
        }
        addComponentList(df, lang, resource, "akvxyz");

        addSubfield0(df, resource);
        addSubfield2(df, resource);
        addSubfield3(df, resource);
        work.addProperty(BIB_FRAME.subject, resource);
        return model;
    }

    @Override
    protected Resource getComponentType(char code) {
        if (code == 'a') return MADS_RDF.Occupation;
        else if (code == 'k') return MADS_RDF.GenreForm;
        else return super.getComponentType(code);
    }
}
