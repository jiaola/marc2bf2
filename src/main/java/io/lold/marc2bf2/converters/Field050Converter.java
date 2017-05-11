package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
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

public class Field050Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field050Converter.class);

    public Field050Converter(Model model, Record record) throws Exception {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("050")) {
            return model;
        }

        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);

        String lang = RecordUtils.getXmlLang(df, record);
        List<Subfield> sfs = df.getSubfields('a');
        for (int i = 0; i < sfs.size(); i++) {
            Subfield sf = sfs.get(i);
            if (sf.getCode() == 'a') {
                if (!FormatUtils.isValidLCC(sf.getData())) {
                    continue;
                }
                Resource resource = model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.ClassificationLcc);
                if (df.getIndicator2() == '0') {
                    String uri = ModelUtils.getUriWithNsPrefix("organizations", "dlc");
                    resource.addProperty(BIB_FRAME.source, model.createResource(uri)
                            .addProperty(RDF.type, BIB_FRAME.Source));
                }
                resource.addProperty(BIB_FRAME.classificationPortion, createLiteral(sf, lang));
                if (i == 0) {
                    Subfield b = RecordUtils.lookAhead(df, i, sfs.size(), 'b');
                    resource.addProperty(BIB_FRAME.itemPortion, createLiteral(b, lang));
                }
                work.addProperty(BIB_FRAME.classification, resource);
            }
        }

        Resource instance = ModelUtils.getInstance(model, record);
        NodeIterator ni = model.listObjectsOfProperty(instance, BIB_FRAME.hasItem);
        int index = ni.toList().size() + 1;
        String itemUri = ModelUtils.buildUri(record, "Item", index);
        Resource item = model.createResource(itemUri)
                .addProperty(RDF.type, BIB_FRAME.Item);

        Subfield sfa = df.getSubfield('a');
        boolean validLcc = false;
        if (sfa != null) {
            validLcc = FormatUtils.isValidLCC(sfa.getData());
        }
        Resource shelfMark = model.createResource()
                .addProperty(RDF.type, validLcc? BIB_FRAME.ShelfMarkLcc : BIB_FRAME.ShelfMark)
                .addProperty(RDFS.label, buildShelfMarkLabel(df));
        if (df.getIndicator2() == '0') {
            shelfMark.addProperty(BIB_FRAME.source,
                    model.createResource("http://id.loc.gov/vocabulary/organizations/dlc")
                        .addProperty(RDF.type, BIB_FRAME.Source));
        }

        item.addProperty(BIB_FRAME.shelfMark, shelfMark);

        for (VariableField f051: record.getVariableFields("051")) {
            DataField df051 = (DataField) f051;
            String str = buildShelfMarkLabel(df051);
            Subfield c = df051.getSubfield('c');
            if (c != null) {
                str += " " + c.getData().trim();
            }
            item.addProperty(BIB_FRAME.shelfMark, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.ShelfMarkLcc)
                    .addProperty(RDFS.label, FormatUtils.chopPunctuation(str)));
        }
        instance.addProperty(BIB_FRAME.hasItem, item);
        item.addProperty(BIB_FRAME.itemOf, instance);

        return model;
    }

    protected String buildShelfMarkLabel(DataField field) {
        String smStr = "";
        Subfield a = field.getSubfield('a');
        if (a != null) {
            smStr = a.getData().trim();
        }
        Subfield b = field.getSubfield('b');
        if (b != null) {
            if (".".equals(StringUtils.substring(b.getData(), 0, 1))) {
                smStr += b.getData().trim();
            } else {
                smStr += " " + b.getData().trim();
            }
        }
        return smStr;
    }

}
