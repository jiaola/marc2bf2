package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
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

public class Field072Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field072Converter.class);

    public Field072Converter(Model model, Record record) throws Exception {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("072")) {
            return model;
        }

        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        String lang = RecordUtils.getXmlLang(df, record);

        String subject = concatSubfields(df, "ax", " ");
        Resource resource = model.createResource()
                .addProperty(RDF.type, RDFS.Resource)
                .addProperty(RDF.value, createLiteral(lang, subject));
        if (df.getIndicator2() == '0') {
            resource.addProperty(BIB_FRAME.source, createLabeledResource(BIB_FRAME.Source, "agricola"));
        } else {
            addSubfield2(df, resource);
        }

        return model;
    }
}
