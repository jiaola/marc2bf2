package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.commons.lang3.StringUtils;
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

import java.util.ArrayList;
import java.util.List;

public class Field034Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field034Converter.class);

    public Field034Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("034")) {
            return model;
        }
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;

        char[] sfchars = {'d', 'e', 'f', 'g'};
        List<String> cList = new ArrayList<>();
        for (char c: sfchars) {
            if (df.getSubfield(c) != null) {
                cList.add(df.getSubfield(c).getData());
            }
        }
        if (!cList.isEmpty()) {
            Resource cart = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Cartographic)
                    .addProperty(BIB_FRAME.coordinates, StringUtils.join(cList, " "));
            List<Subfield> sf3s = df.getSubfields('3');
            for (Subfield three: sf3s) {
                cart.addProperty(BIB_FRAME_LC.appliesTo, SubfieldUtils.mapSubfield3(model, three.getData()));
            }
            work.addProperty(BIB_FRAME.cartographicAttributes, cart);
        }

        List<Subfield> sfbs = df.getSubfields('b');
        for (Subfield b: sfbs) {
            Resource scale = createScale("linear horizontal", b.getData(), df.getSubfields('3'));
            work.addProperty(BIB_FRAME.scale, scale);
        }
        List<Subfield> sfcs = df.getSubfields('c');
        for (Subfield c: sfcs) {
            Resource scale = createScale("linear vertical", c.getData(), df.getSubfields('3'));
            work.addProperty(BIB_FRAME.scale, scale);
        }

        if (sfbs.isEmpty() && sfcs.isEmpty()) {
            List<Subfield> sfas = df.getSubfields('a');
            for (Subfield a: sfas) {
                if ("a".equals(a.getData())) {
                    Resource scale = model.createResource()
                            .addProperty(RDF.type, BIB_FRAME.Scale)
                            .addProperty(BIB_FRAME.note, ModelUtils.createNote(model, "linear scale"));
                    work.addProperty(BIB_FRAME.scale, scale);
                }
            }
        }

        return model;
    }

    private Resource createScale(String label, String data, List<Subfield> sf3s) {
        Resource scale = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Scale)
                .addProperty(BIB_FRAME.note, ModelUtils.createNote(model, label))
                .addProperty(RDFS.label, data);
        for (Subfield three: sf3s) {
            scale.addProperty(BIB_FRAME_LC.appliesTo, SubfieldUtils.mapSubfield3(model, three.getData()));
        }
        return scale;
    }
}
