package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
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

import java.util.List;

public class Field086Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field086Converter.class);

    public Field086Converter(Model model, Record record) throws Exception {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("086")) {
            return model;
        }

        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        String lang = RecordUtils.getXmlLang(df, record);

        List<Subfield> sfs = df.getSubfields("az");
        for (int i = 0; i < sfs.size(); i++) {
            Subfield sf = sfs.get(i);
            Resource resource = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Classification)
                    .addProperty(RDFS.label, sf.getData());
            if (sf.getCode() == 'z') {
                resource.addProperty(BIB_FRAME.status,
                        ModelUtils.createLabeledResource(model, "invalid", BIB_FRAME.Status));
            }
            if (df.getIndicator1() == '0') {
                resource.addProperty(BIB_FRAME.source, ModelUtils.createSource(model, "sudocs"));
            } else if (df.getIndicator1() == '1') {
                resource.addProperty(BIB_FRAME.source,
                        ModelUtils.createSource(model, "Government of Canada Publications"));
            }
            for (Subfield two: df.getSubfields('2')) {
                resource.addProperty(BIB_FRAME.source, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Source)
                        .addProperty(RDFS.label, createLiteral(lang, two.getData())));
            }
            instance.addProperty(BIB_FRAME.classification, resource);
        }
        return model;
    }
}
