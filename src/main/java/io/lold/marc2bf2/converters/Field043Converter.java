package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.ModelFactory;
import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
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

import java.util.List;

public class Field043Converter extends FieldConverter {
    public Field043Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("043")) {
            return model;
        }
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        List<Subfield> sfs = df.getSubfields();
        for (int i = 0 ; i < sfs.size(); i++) {
            Subfield sf = sfs.get(i);
            if (sf.getCode() == 'a') {
                String uri = ModelUtils.getUriWithNsPrefix("geographicAreas",
                        FormatUtils.chopPunctuation(sf.getData(), "[-\\s]+$"));
                Resource gc = model.createResource(uri)
                        .addProperty(RDF.type, BIB_FRAME.GeographicCoverage);
                work.addProperty(BIB_FRAME.geographicCoverage, gc);
            } else if (sf.getCode() == 'b' || sf.getCode() == 'c') {
                Resource gc = model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.GeographicCoverage)
                        .addProperty(RDFS.label, sf.getData());
                if (sf.getCode() == 'b') {
                    Subfield two = RecordUtils.lookAhead(df, i, 2, '2');
                    if (two != null) {
                        gc.addProperty(BIB_FRAME.source, SubfieldUtils.mapSubfield2(model, two.getData()));
                    }
                } else {
                    gc.addProperty(BIB_FRAME.source, model.createResource().
                            addProperty(RDF.type, BIB_FRAME.Source)
                            .addProperty(RDFS.label, "ISO 3166"));
                }
                work.addProperty(BIB_FRAME.geographicCoverage, gc);
            } else if (sf.getCode() == '0') {
                Resource r = SubfieldUtils.mapSubfield0(model, sf.getData());
                if (r == null) continue;
                if (r.hasProperty(BIB_FRAME.source)) {
                    work.addProperty(BIB_FRAME.identifiedBy, r);
                } else {
                    work.addProperty(BIB_FRAME.agent, r);
                }
            }
        }
        return model;
    }
}
