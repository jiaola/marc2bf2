package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field026Converter extends FieldConverter {
    public Field026Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;
        String joined = concatSubfields(df, "abcd", " ");

        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Fingerprint);
        if (StringUtils.isBlank(joined)) {
            for (Subfield e: df.getSubfields('e')) {
                resource.addProperty(RDF.value, e.getData());
            }
        } else {
            resource.addProperty(RDF.value, joined);
        }
        addSubfield2(df, resource);
        addSubfield5(df, resource);

        ModelUtils.getInstance(model, record)
                .addProperty(BIB_FRAME.identifiedBy, resource);
        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "026".equals(getTag(field));
    }
}
