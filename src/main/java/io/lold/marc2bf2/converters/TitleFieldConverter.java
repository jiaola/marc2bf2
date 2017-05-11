package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;

public abstract class TitleFieldConverter extends FieldConverter {
    public TitleFieldConverter(Model model, Record record) {
        super(model, record);
    }

    protected void addSubfields(DataField field, String subfields, Resource resource) {
        for (char sf: subfields.toCharArray()) {
            addSubfield(field, sf, resource);
        }
    }

    protected void addSubfield(DataField field, char subfield, Resource resource) {
        Property property = null;
        if (subfield == 'a') {
            property = BIB_FRAME.mainTitle;
        } else if (subfield == 'b') {
            if ("210".equals(field.getTag()) || "222".equals(field.getTag())) {
                property = BIB_FRAME.qualifier;
            } else {
                property = BIB_FRAME.subtitle;
            }
        } else if (subfield == 'f') {
            property = BIB_FRAME.date;
        } else if (subfield == 'g') {
            property = BIB_FRAME.qualifier;
        } else if (subfield == 'n') {
            property = BIB_FRAME.partNumber;
        } else if (subfield == 'p') {
            property = BIB_FRAME.partName;
        } else if (subfield == 's') {
            property = BIB_FRAME.version;
        } else {
            return;
        }

        String lang = RecordUtils.getXmlLang(field, record);
        for (Subfield sf: field.getSubfields(subfield)) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(property, createLiteral(value, lang));
        }
    }
}
