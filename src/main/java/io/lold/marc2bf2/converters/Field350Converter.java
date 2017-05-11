package io.lold.marc2bf2.converters;

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

public class Field350Converter extends Field344Converter {
    public Field350Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("350")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        String lang = RecordUtils.getXmlLang(df, record);
        for (Subfield sf: df.getSubfields('a')) {
            Resource resource = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.AcquisitionSource)
                    .addProperty(BIB_FRAME.acquisitionTerms, createLiteral(sf.getData(), lang));
            instance.addProperty(BIB_FRAME.acquisitionSource, resource);
        }
        return model;
    }

}
