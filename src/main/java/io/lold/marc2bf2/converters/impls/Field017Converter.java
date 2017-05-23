package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.InstanceIdConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.datatypes.xsd.impl.XSDDateType;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field017Converter extends InstanceIdConverter {
    public Field017Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;

        Resource instance = ModelUtils.getInstance(model, record);
        List<Resource> resources = convert(field, BIB_FRAME.CopyrightNumber);
        for (Resource resource: resources) {
            Subfield d = df.getSubfield('d');
            if (d != null) {
                String date = FormatUtils.formatDate8d(d.getData());
                resource.addProperty(BIB_FRAME.date, model.createTypedLiteral(date, new XSDDateType("date")));
            }

            for (Subfield i: df.getSubfields('i')) {
                String value = FormatUtils.chopPunctuation(i.getData(), "[;,:/\\s]+$");
                resource.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, value));
            }

            for (Resource source: convertSubfieldB(df)) {
                resource.addProperty(BIB_FRAME.source, source);
            }

            instance.addProperty(BIB_FRAME.identifiedBy, resource);
        }

        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "017".equals(getTag(field));
    }
}
