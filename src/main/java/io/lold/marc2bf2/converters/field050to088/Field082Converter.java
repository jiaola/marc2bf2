package io.lold.marc2bf2.converters.field050to088;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
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

public class Field082Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field082Converter.class);

    public Field082Converter(Model model, Record record) throws Exception {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("082")) {
            return model;
        }

        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        String lang = RecordUtils.getXmlLang(df, record);

        List<Subfield> sfas = df.getSubfields('a');
        for (int i = 0; i < sfas.size(); i++) {
            Subfield a = sfas.get(i);
            Resource resource = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.ClassificationDdc)
                    .addProperty(BIB_FRAME.classificationPortion,
                            createLiteral(a.getData(), lang));
            if (i == 0) {
                for (Subfield b: df.getSubfields('b')) {
                    resource.addProperty(BIB_FRAME.itemPortion,
                            createLiteral(b.getData(), lang));
                }
            }
            for (Subfield two: df.getSubfields('2')) {
                resource.addProperty(BIB_FRAME.edition,
                        createLiteral(two.getData(), lang));
            }
            if (df.getIndicator1() == '0') {
                resource.addProperty(BIB_FRAME.edition, "full");
            } else if (df.getIndicator1() == '1') {
                resource.addProperty(BIB_FRAME.edition, "abridged");
            }
            for (Subfield q: df.getSubfields('q')) {
                resource.addProperty(BIB_FRAME.source, SubfieldUtils.mapSubfield2(model, q.getData()));
            }
            if (df.getIndicator2() == '0') {
                String uri = ModelUtils.getUriWithNsPrefix("organizations", "dlc");
                resource.addProperty(BIB_FRAME.source, model.createResource(uri)
                        .addProperty(RDF.type, BIB_FRAME.Source));
            }
            work.addProperty(BIB_FRAME.classification, resource);
        }
        return model;
    }
}
