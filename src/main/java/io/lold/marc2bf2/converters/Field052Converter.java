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

public class Field052Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field052Converter.class);

    public Field052Converter(Model model, Record record) throws Exception {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("052")) {
            return model;
        }

        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);
        String place = RecordUtils.getSubfieldData(df, 'a');
        String lang = RecordUtils.getXmlLang(df, record);
        List<Subfield> sfbs = df.getSubfields('b');
        if (sfbs.isEmpty()) {
            work.addProperty(BIB_FRAME.geographicCoverage, createPlace(df, place, lang));
        } else {
            for (Subfield b: sfbs) {
                work.addProperty(BIB_FRAME.geographicCoverage,
                        createPlace(df, place + " " + b.getData(), lang));
            }
        }
        return model;
    }

    private Resource createPlace(DataField df, String place, String lang) {
        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Place)
                .addProperty(RDF.value, place);
        for (Subfield d: df.getSubfields('d')) {
            resource.addProperty(RDFS.label, createLiteral(d, lang));
        }
        if (df.getIndicator1() == ' ') {
            String uri = ModelUtils.getUriWithNsPrefix("classSchemes", "lcc");
            resource.addProperty(BIB_FRAME.source, model.createResource(uri)
                    .addProperty(RDF.type, BIB_FRAME.Source));
        }
        return resource;
    }
}
