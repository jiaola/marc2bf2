package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field243Converter extends FieldConverter {
    public Field243Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("243")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Title)
                .addProperty(RDF.type, BIB_FRAME.VariantTitle)
                .addProperty(RDF.type, BIB_FRAME.CollectiveTitle);
        String lang = RecordUtils.getXmlLang(df, record);
        String label = concatSubfields(df, "adfgklmnoprs", " ");
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(RDFS.label, createLiteral(lang, label))
                    .addProperty(BIB_FRAME_LC.titleSortKey, label);
        }

        for (Subfield sf: df.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.mainTitle, createLiteral(lang, value));
        }

        work.addProperty(BIB_FRAME.title, resource);
        return model;
    }
}
