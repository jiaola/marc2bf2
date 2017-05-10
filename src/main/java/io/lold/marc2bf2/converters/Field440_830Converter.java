package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.FormatUtils;
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

public class Field440_830Converter extends NameTitleFieldConverter {
    public Field440_830Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!"440".equals(field.getTag()) && !"830".equals(field.getTag())) {
            return model;
        }

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
            work.addProperty(BIB_FRAME.seriesEnumeration, createLiteral(lang, value));
        }
        return model;
    }

}
