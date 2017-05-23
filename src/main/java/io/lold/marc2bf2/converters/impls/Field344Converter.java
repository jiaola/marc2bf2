package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field344Converter extends FieldConverter {
    public Field344Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        processSubfield(instance, df, 'a', BIB_FRAME.soundCharacteristic, BIB_FRAME.RecordingMethod);
        processSubfield(instance, df, 'b', BIB_FRAME.soundCharacteristic, BIB_FRAME.RecordingMedium);
        processSubfield(instance, df, 'c', BIB_FRAME.soundCharacteristic, BIB_FRAME.PlayingSpeed);
        processSubfield(instance, df, 'd', BIB_FRAME.soundCharacteristic, BIB_FRAME.GrooveCharacteristic);
        processSubfield(instance, df, 'e', BIB_FRAME.soundCharacteristic, BIB_FRAME.TrackConfig);
        processSubfield(instance, df, 'f', BIB_FRAME.soundCharacteristic, BIB_FRAME.TapeConfig);
        processSubfield(instance, df, 'g', BIB_FRAME.soundCharacteristic, BIB_FRAME.PlaybackChannels);
        processSubfield(instance, df, 'h', BIB_FRAME.soundCharacteristic, BIB_FRAME.PlaybackCharacteristic);

        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "344".equals(getTag(field));
    }

    protected void processSubfield(Resource instance, DataField field, char code, Property property, Resource type) {
        String lang = RecordUtils.getXmlLang(field, record);
        for (Subfield sf: field.getSubfields(code)) {
            Resource resource = createLabeledResource(type, sf.getData(), lang);
            addSubfield2(field, resource);
            addSubfield3(field, resource);
            instance.addProperty(property, resource);
        }
    }
}
