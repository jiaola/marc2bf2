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
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Field072Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field072Converter.class);

    public Field072Converter(Model model, Record record) throws Exception {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);


        String subject = concatSubfields(df, "ax", " ");
        Resource resource = model.createResource()
                .addProperty(RDF.type, RDFS.Resource)
                .addProperty(RDF.value, createLiteral(subject, lang));
        if (df.getIndicator2() == '0') {
            resource.addProperty(BIB_FRAME.source, createLabeledResource(BIB_FRAME.Source, "agricola"));
        } else {
            addSubfield2(df, resource);
        }
        work.addProperty(BIB_FRAME.subject, resource);

        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "072".equals(field.getTag());
    }
}
