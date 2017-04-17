package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field028Converter extends InstanceIdConverter {
    public Field028Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!"028".equals(field.getTag())) {
            return model;
        }
        Resource instance = ModelUtils.getInstance(model, record);
        Resource identifier;
        DataField df = (DataField) field;
        switch (df.getIndicator1()) {
            case '0':
                identifier = BIB_FRAME.AudioIssueNumber;
                break;
            case '1':
                identifier = BIB_FRAME.MatrixNumber;
                break;
            case '2':
                identifier = BIB_FRAME.MusicPlate;
                break;
            case '3':
                identifier = BIB_FRAME.MusicDistributorNumber;
                break;
            case '4':
                identifier = BIB_FRAME.VideoRecordingNumber;
                break;
            default:
                identifier = BIB_FRAME.PublisherNumber;
                break;
        }
        List<Resource> resources = convert(field, identifier);
        for (Resource resource: resources) {
            for (Resource source: convertSubfieldB(df)) {
                resource.addProperty(BIB_FRAME.source, source);
            }
            instance.addProperty(BIB_FRAME.identifiedBy, resource);
        }
        return model;
    }
}
