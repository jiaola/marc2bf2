package io.lold.marc2bf2.converters.field648to662;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import io.lold.marc2bf2.vocabulary.MADS_RDF;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFList;
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

public class Field655Converter extends Field648Converter {
    public Field655Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        DataField df = (DataField) field;
        if (!"655".equals(getTag(df)) || df.getIndicator1() != ' ') {
            return model;
        }

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
            uri = ModelUtils.buildUri(record, "GenreForm", getTag(df), fieldIndex);
        }

        Resource madsClass = df.getSubfields("vxyz").isEmpty() ?
                MADS_RDF.GenreForm : MADS_RDF.ComplexSubject;
        Resource resource = model.createResource(uri)
                .addProperty(RDF.type, BIB_FRAME.GenreForm)
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

        for (Subfield sf: df.getSubfields('g')) {
            resource.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, sf.getData(), lang));
        }
        for (Resource role: contributionRelationship(df.getSubfields('e'), lang, work)) {
            resource.addProperty(BIB_FRAME_LC.relationship, role.addProperty(BIB_FRAME.relatedTo, work));
        }
        addSubfield0(df, resource);
        addSubfield5(df, resource);
        addSourceCode(df, resource);

        work.addProperty(BIB_FRAME.genreForm, resource);
        return model;
    }


}
