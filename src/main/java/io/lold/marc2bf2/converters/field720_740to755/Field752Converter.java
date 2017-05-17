package io.lold.marc2bf2.converters.field720_740to755;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.converters.field648to662.Field662Converter;
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

public class Field752Converter extends Field662Converter {
    public Field752Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        String lang = RecordUtils.getXmlLang(df, record);

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
        resource.addProperty(RDF.type, BIB_FRAME.Place)
                .addProperty(RDF.type, MADS_RDF.HierarchicalGeographic);

        String label = FormatUtils.chopPunctuation(concatSubfields(df, "abcdfgh", "--"));
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(RDFS.label, createLiteral(label, lang));
            resource.addProperty(MADS_RDF.authoritativeLabel, createLiteral(label, lang));
        }
        addComponentList(df, lang, resource, "abcdfgh");
        addSubfield0AndW(sf0orws, resource);
        addSubfield2(df, resource);
        for (Resource role: contributionRelationship(df.getSubfields('e'), lang, work)) {
            resource.addProperty(BIB_FRAME_LC.relationship, role.addProperty(BIB_FRAME.relatedTo, work));
        }
        for (Subfield sf: df.getSubfields('4')) {
            String relator = ModelUtils.getUriWithNsPrefix("relators", StringUtils.substring(sf.getData(), 0, 3));
            resource.addProperty(BIB_FRAME_LC.relationship, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME_LC.Relationship)
                    .addProperty(BIB_FRAME_LC.relation, model.createResource(relator))
                    .addProperty(BIB_FRAME.relatedTo, work));
        }
        work.addProperty(BIB_FRAME.place, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "752".equals(field.getTag());
    }

}
