package io.lold.marc2bf2.converters.field240_X30;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
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

public class Field730_740Converter extends NameTitleFieldConverter {
    public Field730_740Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!"730".equals(field.getTag()) && !"740".equals(field.getTag())) {
            return model;
        }

        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        String workUri = buildNewWorkUri(df);

        Resource resource = model.createResource(workUri)
                .addProperty(RDF.type, BIB_FRAME.Work);
        addUniformTitle(df, resource);
        if (df.getIndicator2() == '2') {
            work.addProperty(BIB_FRAME.hasPart, resource);
        } else {
            work.addProperty(BIB_FRAME.relatedTo, resource);
        }
        String lang = RecordUtils.getXmlLang(df, record);
        for (Subfield sf: df.getSubfields('i')) {
            Resource relationship = createRelationship(FormatUtils.chopPunctuation(sf.getData()), work.getURI(), lang);
            work.addProperty(BIB_FRAME_LC.relationship, relationship);
        }
        return model;
    }

}
