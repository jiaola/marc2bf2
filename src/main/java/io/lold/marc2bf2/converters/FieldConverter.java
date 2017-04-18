package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

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
            Resource source = SubfieldUtils.mapSubfield2(model, sf2.getData());
            resource.addProperty(BIB_FRAME.source, source);
        }
    }

    protected void addSubfield5(DataField field, Resource resource) {
        for (Subfield sf5: field.getSubfields('5')) {
            Resource agent = SubfieldUtils.mapSubfield5(model, sf5.getData());
            resource.addProperty(BIB_FRAME_LC.applicableInstitution, agent);
        }
    }

    protected  void addSubfield3(DataField field, Resource resource) {
        for (Subfield sf3: field.getSubfields('3')) {
            Resource appliesTo = SubfieldUtils.mapSubfield3(model, sf3.getData());
            resource.addProperty(BIB_FRAME_LC.appliesTo, appliesTo);
        }
    }
}
