package io.lold.marc2bf2.converters.field1XX_6XX_7XX_8XX;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
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
    protected Model process(VariableField field) throws Exception {
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
    @Override
    public boolean checkField(VariableField field) {
        String tag = field.getTag();
        return "800".equals(tag) || "810".equals(tag) || "811".equals(tag);
    }
}
