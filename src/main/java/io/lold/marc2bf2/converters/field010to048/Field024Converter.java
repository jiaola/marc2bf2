package io.lold.marc2bf2.converters.field010to048;

import io.lold.marc2bf2.converters.InstanceIdConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field024Converter extends InstanceIdConverter {
    public Field024Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;

        Resource instance = ModelUtils.getInstance(model, record);
        Resource identifier;
        switch (df.getIndicator1()) {
            case '0':
                identifier = BIB_FRAME.Isrc;
                break;
            case '1':
                identifier = BIB_FRAME.Upc;
                break;
            case '2':
                identifier = BIB_FRAME.Ismn;
                break;
            case '3':
                identifier = BIB_FRAME.Ean;
                break;
            case '4':
                identifier = BIB_FRAME.Sici;
                break;
            default:
                identifier = BIB_FRAME.Identifier;
                break;
        }
        List<Resource> resources = convert(field, identifier);
        for (Resource resource: resources) {
            if (df.getIndicator1() == '7') {
                for (Subfield two: df.getSubfields('2')) {
                    resource.addProperty(RDFS.label, two.getData());
                }
            }
            instance.addProperty(BIB_FRAME.identifiedBy, resource);
        }

        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "024".equals(field.getTag());
    }
}
