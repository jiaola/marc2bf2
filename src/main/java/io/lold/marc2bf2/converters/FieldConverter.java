package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;
import java.util.stream.Collectors;

public abstract class FieldConverter {
    protected Model model;
    protected Record record;
    public FieldConverter(Model model, Record record) {
        this.model = model;
        this.record = record;
    }

    public abstract Model convert(VariableField field) throws Exception;

    protected void addSubfield2(DataField field, Resource resource) {
        for (Subfield sf2: field.getSubfields('2')) {
            resource.addProperty(BIB_FRAME.source, SubfieldUtils.mapSubfield2(model, sf2.getData()));
        }
    }

    protected void addSubfield5(DataField field, Resource resource) {
        for (Subfield sf5: field.getSubfields('5')) {
            Resource agent = SubfieldUtils.mapSubfield5(model, sf5.getData());
            resource.addProperty(BIB_FRAME_LC.applicableInstitution, agent);
        }
    }

    protected void addSubfield3(DataField field, Resource resource) {
        for (Subfield sf3: field.getSubfields('3')) {
            Resource appliesTo = SubfieldUtils.mapSubfield3(model, sf3.getData());
            resource.addProperty(BIB_FRAME_LC.appliesTo, appliesTo);
        }
    }

    protected Literal createLiteral(String lang, Subfield subfield) {
        return StringUtils.isBlank(lang) ?
                model.createLiteral(subfield.getData(), lang) :
                model.createLiteral(subfield.getData());
    }

    protected Literal createLiteral(Subfield subfield) {
        return createLiteral(null, subfield);
    }

    protected Literal createLiteral(String lang, String value) {
        return StringUtils.isBlank(lang) ?
                model.createLiteral(value, lang) :
                model.createLiteral(value);
    }

    protected String concatSubfields(DataField field, String subfields, String separator) {
        List<Subfield> sfs = field.getSubfields(subfields);
        List<String> datas = sfs.stream().map(sf -> sf.getData().trim()).collect(Collectors.toList());
        return StringUtils.join(datas, separator);
    }
}
