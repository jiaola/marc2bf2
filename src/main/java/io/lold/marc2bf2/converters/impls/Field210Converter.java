package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.*;

public class Field210Converter extends FieldConverter {
    public Field210Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Title)
                .addProperty(RDF.type, BIB_FRAME.VariantTitle)
                .addProperty(RDF.type, BIB_FRAME.AbbreviatedTitle);
        if (df.getIndicator2() == ' ') {
            resource.addProperty(BIB_FRAME.source, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Source)
                    .addProperty(RDF.value, "issnkey"));
        }

        String label = concatSubfields(df, "ab", " ");
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(RDFS.label, createLiteral(label, lang))
                    .addProperty(BIB_FRAME_LC.titleSortKey, label);
        }
        for (Subfield sf: df.getSubfields('a')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.mainTitle, createLiteral(value, lang));
        }
        for (Subfield sf: df.getSubfields('b')) {
            String value = FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData()));
            resource.addProperty(BIB_FRAME.qualifier, value);
        }
        addSubfield2(df, resource);
        instance.addProperty(BIB_FRAME.title, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "210".equals(getTag(field));
    }
}
