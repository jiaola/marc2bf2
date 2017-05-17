package io.lold.marc2bf2.converters.field1XX_6XX_7XX_8XX;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field7XXNameConverter extends NameTitleFieldConverter {
    public Field7XXNameConverter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        String relatedToUri;
        if (!df.getSubfields('t').isEmpty()) {
            String uri = ModelUtils.buildUri(record, "Work", getTag(df), fieldIndex);
            Resource resource = model.createResource(uri)
                    .addProperty(RDF.type, BIB_FRAME.Work)
                    .addProperty(BIB_FRAME.contribution, buildContribution(df));
            addUniformTitle(df, resource);
            if (df.getIndicator2() == '2') {
                work.addProperty(BIB_FRAME.hasPart, resource);
            } else {
                work.addProperty(BIB_FRAME.relatedTo, resource);
            }
            relatedToUri = ModelUtils.buildUri(record, "Work", getTag(df), fieldIndex);
        } else {
            work.addProperty(BIB_FRAME.contribution, buildContribution(df));
            relatedToUri = ModelUtils.buildUri(record, "Agent", getTag(df), fieldIndex);
        }
        for (Subfield sfi: df.getSubfields('i')) {
            work.addProperty(BIB_FRAME_LC.relationship, createRelationship(sfi.getData(), model.getResource(relatedToUri), lang));
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        String tag = field.getTag();
        return "700".equals(tag) || "710".equals(tag) || "711".equals(tag);
    }

}
