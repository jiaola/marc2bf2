package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Field047Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field047Converter.class);

    public Field047Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        List<Subfield> sfas = df.getSubfields('a');
        for (Subfield a: sfas) {
            Resource resource;
            if (df.getIndicator2() == ' ') {
                String uri = ModelUtils.getUriWithNsPrefix("marcmuscomp", a.getData());
                resource = model.createResource(uri)
                        .addProperty(RDF.type, BIB_FRAME.GenreForm)
                        .addProperty(BIB_FRAME.source, createLabeledResource(BIB_FRAME.Source, "marcmuscomp"));
            } else {
                resource = model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.GenreForm)
                        .addProperty(BIB_FRAME.code, a.getData());
                addSubfield2(df, resource);
            }
            work.addProperty(BIB_FRAME.genreForm, resource);
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "047".equals(field.getTag());
    }
}
