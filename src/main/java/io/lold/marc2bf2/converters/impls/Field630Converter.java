package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
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
    protected Model process(VariableField field) throws Exception {
        Resource work = ModelUtils.getWork(model, record);

        DataField df = (DataField) field;
        String workUri = buildNewWorkUri(df);
        Resource resource = model.createResource(workUri)
                .addProperty(RDF.type, BIB_FRAME.Work);
        addMads(df, resource, titleLabel(df));
        Resource source = buildSource(df);
        if (source != null) {
            resource.addProperty(BIB_FRAME.source, source);
        }

        List<Resource> relationships = contributionRelationship(df.getSubfields('e'), lang, work);
        for (Resource relationship: relationships) {
            resource.addProperty(BIB_FRAME_LC.relationship, relationship);
        }
        for (Subfield sf: df.getSubfields('4')) {
            String code = StringUtils.substring(sf.getData(), 0, 3);
            resource.addProperty(BIB_FRAME_LC.relationship, createRelationship(code, work));
        }
        addUniformTitle(df, resource);
        work.addProperty(BIB_FRAME.subject, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "630".equals(getTag(field));
    }
}
