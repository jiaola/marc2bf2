package io.lold.marc2bf2.converters.field050to088;

import io.lold.marc2bf2.converters.FieldConverter;
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

public class Field084Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field084Converter.class);

    public Field084Converter(Model model, Record record) throws Exception {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);


        List<Subfield> sfas = df.getSubfields('a');
        for (int i = 0; i < sfas.size(); i++) {
            Subfield a = sfas.get(i);
            Resource resource = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Classification)
                    .addProperty(BIB_FRAME.classificationPortion,
                            createLiteral(a.getData(), lang));
            if (i == 0) {
                for (Subfield b: df.getSubfields('b')) {
                    resource.addProperty(BIB_FRAME.itemPortion,
                            createLiteral(b.getData(), lang));
                }
            }
            List<Subfield> sfqs = df.getSubfields('q');
            if (!sfqs.isEmpty()) {
                Resource am = model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.AdminMetadata);
                for (Subfield q: sfqs) {
                    am.addProperty(BIB_FRAME.assigner, model.createResource()
                            .addProperty(RDF.type, BIB_FRAME.Agent)
                            .addProperty(RDFS.label, createLiteral(q.getData(), lang)));
                }
                resource.addProperty(BIB_FRAME.adminMetadata, am);
            }
            addSubfield2(df, resource);
            work.addProperty(BIB_FRAME.classification, resource);
        }
        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "084".equals(field.getTag());
    }
}
