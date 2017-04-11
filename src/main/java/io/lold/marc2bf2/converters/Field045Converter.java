package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.ModelFactory;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.DataTypes;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
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
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("045")) {
            return model;
        }
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
}
