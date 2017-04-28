package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
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

public class Field630Converter extends NameTitleFieldConverter {
    public Field630Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!"630".equals(field.getTag())) {
            return model;
        }

        Resource work = ModelUtils.getWork(model, record);

        DataField df = (DataField) field;
        String workUri = ModelUtils.buildUri(record, "Work", getTag(df), fieldIndex);
        Resource resource = model.createResource(workUri)
                .addProperty(RDF.type, BIB_FRAME.Work);
        addMads(df, resource, titleLabel(df));
        resource.addProperty(BIB_FRAME.source, buildSource(df));
        String lang = RecordUtils.getXmlLang(df, record);
        List<Resource> relationships = contributionRelationship(df.getSubfields('e'), lang, work.getURI());
        for (Resource relationship: relationships) {
            resource.addProperty(BIB_FRAME_LC.relationship, relationship);
        }
        for (Subfield sf: df.getSubfields('4')) {
            String code = StringUtils.substring(sf.getData(), 0, 3);
            resource.addProperty(BIB_FRAME_LC.relationship, createRelationship(code, work.getURI()));
        }
        addUniformTitle(df, resource);
        work.addProperty(BIB_FRAME.subject, resource);
        return model;
    }

}