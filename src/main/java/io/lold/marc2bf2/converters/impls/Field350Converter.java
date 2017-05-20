package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field350Converter extends Field344Converter {
    public Field350Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        for (Subfield sf: df.getSubfields('a')) {
            Resource resource = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.AcquisitionSource)
                    .addProperty(BIB_FRAME.acquisitionTerms, createLiteral(sf.getData(), lang));
            instance.addProperty(BIB_FRAME.acquisitionSource, resource);
        }
        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "350".equals(field.getTag());
    }
}
