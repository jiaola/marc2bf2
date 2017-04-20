package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import io.lold.marc2bf2.vocabulary.DataTypes;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.datatypes.BaseDatatype;
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
import java.util.stream.Collectors;

public class Field033Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field033Converter.class);

    public Field033Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("033")) {
            return model;
        }
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;

        Resource capture = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Capture);
        if (df.getIndicator2() == '1') {
            capture.addProperty(BIB_FRAME.note, ModelUtils.createNote(model, "broadcast"));
        } else if ("2".equals(df.getIndicator2())) {
            capture.addProperty(BIB_FRAME.note, ModelUtils.createNote(model, "finding"));
        }

        if (df.getIndicator1() == '0') {
            String vdate = FormatUtils.formatEDTF(df.getSubfield('a').getData());
            capture.addProperty(BIB_FRAME.date,
                    model.createTypedLiteral(vdate, DataTypes.EDTF));
        } else if (df.getIndicator1() == '2') {
            List<String> dates = df.getSubfields('a').stream().
                    map(sf -> FormatUtils.formatEDTF(sf.getData()))
                    .collect(Collectors.toList());
            String vdate = StringUtils.join(dates, '/');
            capture.addProperty(BIB_FRAME.date,
                    model.createTypedLiteral(vdate, DataTypes.EDTF));
        } else if (df.getIndicator1() == '1') {
            List<Subfield> sfs = df.getSubfields('a');
            for (Subfield sf: sfs) {
                String vdate = FormatUtils.formatEDTF(sf.getData());
                capture.addProperty(BIB_FRAME.date,
                        model.createTypedLiteral(vdate, DataTypes.EDTF));
            }
        }

        List<Subfield> sfbs = df.getSubfields('b');
        List<Subfield> sfcs = df.getSubfields('c');
        int size = sfbs.size();
        if (sfbs.size() != sfcs.size()) {
            logger.warn("$b not followed by $c");
            size = Math.min(sfbs.size(), sfcs.size());
        }
        for (int i = 0; i < size; i++) {
            Subfield b = sfbs.get(i);
            Subfield c = sfcs.get(i);
            Resource place = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Place)
                    .addProperty(RDF.value, b.getData() + " " + c.getData())
                    .addProperty(BIB_FRAME.source, model.createResource()
                            .addProperty(RDF.type, BIB_FRAME.Source)
                            .addProperty(RDFS.label, "lcc-g")
                    );
            capture.addProperty(BIB_FRAME.place, place);
        }

        List<Subfield> sfps = df.getSubfields('p');
        List<Subfield> sf2s = df.getSubfields('2');
        size = sfps.size();
        if (sfps.size() != sf2s.size()) {
            logger.warn("$b not followed by $c");
            size = Math.min(sfps.size(), sf2s.size());
        }
        for (int i = 0; i < size; i++) {
            Subfield p = sfps.get(i);
            Subfield two = sf2s.get(i);
            Resource place = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Place)
                    .addProperty(RDF.value, p.getData())
                    .addProperty(BIB_FRAME.source, SubfieldUtils.mapSubfield2(model, two.getData()));
            capture.addProperty(BIB_FRAME.place, place);
        }

        addSubfield3(df, capture);

        return model;
    }
}
