package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field8XXNameConverter extends NameTitleFieldConverter {
    public Field8XXNameConverter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!"800".equals(field.getTag()) && !"810".equals(field.getTag()) && !"811".equals(field.getTag())) {
            return model;
        }

        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        String uri = ModelUtils.buildUri(record, "Work", getTag(df), fieldIndex);
        Resource seriesWork = model.createResource(uri)
                .addProperty(RDF.type, BIB_FRAME.Work)
                .addProperty(BIB_FRAME.contribution, buildContribution(df));
        addUniformTitle(df, seriesWork);
        work.addProperty(BIB_FRAME.hasSeries, seriesWork);
        String lang = RecordUtils.getXmlLang(df, record);
        for (Subfield sfv: df.getSubfields('v')) {
            String value = FormatUtils.chopPunctuation(sfv.getData());
            work.addProperty(BIB_FRAME.seriesEnumeration, createLiteral(value, lang));
        }

        return model;
    }

}
