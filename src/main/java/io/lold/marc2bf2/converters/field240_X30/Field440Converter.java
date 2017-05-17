package io.lold.marc2bf2.converters.field240_X30;

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

public class Field440Converter extends NameTitleFieldConverter {
    public Field440Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        String workUri = buildNewWorkUri(df);
        Resource resource = model.createResource(workUri)
                .addProperty(RDF.type, BIB_FRAME.Work);

        addUniformTitle(df, resource);
        work.addProperty(BIB_FRAME.hasSeries, resource);
        String lang = RecordUtils.getXmlLang(df, record);
        for (Subfield sf: df.getSubfields('v')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            work.addProperty(BIB_FRAME.seriesEnumeration, createLiteral(value, lang));
        }
        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "440".equals(field.getTag()) || "830".equals(field.getTag());
    }
}
