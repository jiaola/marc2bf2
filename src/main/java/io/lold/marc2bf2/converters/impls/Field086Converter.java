package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Field086Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field086Converter.class);

    public Field086Converter(Model model, Record record) throws Exception {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        for (Subfield sf: df.getSubfields("az")) {
            Resource resource = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Classification)
                    .addProperty(RDFS.label, sf.getData());
            if (sf.getCode() == 'z') {
                resource.addProperty(BIB_FRAME.status,
                        createLabeledResource(BIB_FRAME.Status, "invalid"));
            }
            if (df.getIndicator1() == '0') {
                resource.addProperty(BIB_FRAME.source, createLabeledResource(BIB_FRAME.Source, "sudocs"));
            } else if (df.getIndicator1() == '1') {
                resource.addProperty(BIB_FRAME.source,
                        createLabeledResource(BIB_FRAME.Source,"Government of Canada Publications"));
            }
            for (Subfield two: df.getSubfields('2')) {
                resource.addProperty(BIB_FRAME.source, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Source)
                        .addProperty(RDFS.label, createLiteral(two.getData(), lang)));
            }
            instance.addProperty(BIB_FRAME.classification, resource);
        }
        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "086".equals(getTag(field));
    }
}
