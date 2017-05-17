package io.lold.marc2bf2.converters.field010to048;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.DataTypes;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field045Converter extends FieldConverter {
    public Field045Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        List<Subfield> sfas = df.getSubfields('a');
        for (Subfield a: sfas) {
            String v = FormatUtils.format045a(a.getData());
            // TODO: Catch MarcFormatException
            work.addProperty(BIB_FRAME.temporalCoverage, model.createTypedLiteral(v, DataTypes.EDTF));
        }

        List<Subfield> sfbs = df.getSubfields('b');
        if (df.getIndicator1() == '2') {
            String v0 = FormatUtils.format045b(sfbs.get(0).getData());
            String v1 = FormatUtils.format045b(sfbs.get(1).getData());
            work.addProperty(BIB_FRAME.temporalCoverage, model.createTypedLiteral(v0+"/"+v1, DataTypes.EDTF));
        } else {
            for (Subfield b: sfbs) {
                String v = FormatUtils.format045b(b.getData());
                work.addProperty(BIB_FRAME.temporalCoverage, model.createTypedLiteral(v, DataTypes.EDTF));
            }
        }

        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "045".equals(field.getTag());
    }
}
