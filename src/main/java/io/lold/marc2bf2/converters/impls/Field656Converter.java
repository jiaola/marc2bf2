package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.utils.ModelUtils;
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

import java.util.List;

public class Field656Converter extends Field648Converter {
    public Field656Converter(Model model, Record record) {
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

        addSubfield0AndW(sf0orws, resource);
        addSubfield2(df, resource);
        addSubfield3(df, resource);
        work.addProperty(BIB_FRAME.subject, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "656".equals(field.getTag());
    }

    @Override
    protected Resource getComponentType(char code) {
        if (code == 'a') return MADS_RDF.Occupation;
        else if (code == 'k') return MADS_RDF.GenreForm;
        else return super.getComponentType(code);
    }
}
