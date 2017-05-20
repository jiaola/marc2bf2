package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field037Converter extends FieldConverter {
    public Field037Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;

        String acqSource = null;
        if (df.getIndicator1() == '2') {
            acqSource = "intervening source";
        } else if (df.getIndicator1() == '3') {
            acqSource = "current source";
        }
        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.AcquisitionSource);
        if (StringUtils.isNotBlank(acqSource)) {
            resource.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, acqSource));
        }

        for (Subfield a: df.getSubfields('a')) {
            resource.addProperty(BIB_FRAME.identifiedBy, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.StockNumber)
                    .addProperty(RDF.value, a.getData()));
        }
        for (Subfield b: df.getSubfields('b')) {
            resource.addProperty(RDFS.label, b.getData());
        }
        for (Subfield c: df.getSubfields('c')) {
            resource.addProperty(BIB_FRAME.acquisitionTerms, c.getData());
        }
        for (Subfield f: df.getSubfields('f')) {
            resource.addProperty(BIB_FRAME.note,
                    createLabeledResource(BIB_FRAME.Note, f.getData()));
        }
        for (Subfield gn: df.getSubfields("gn")) {
            resource.addProperty(BIB_FRAME.note,
                    createLabeledResource(BIB_FRAME.Note, gn.getData()));
        }

        addSubfield3(df, resource);
        addSubfield5(df, resource);

        ModelUtils.getInstance(model, record)
                .addProperty(BIB_FRAME.acquisitionSource, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "037".equals(field.getTag());
    }
}
