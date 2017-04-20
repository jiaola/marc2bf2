package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field035Converter extends InstanceIdConverter {
    public Field035Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!"035".equals(field.getTag())) {
            return model;
        }
        Resource instance = ModelUtils.getInstance(model, record);
        DataField df = (DataField) field;
        List<Resource> resources = convert(field, BIB_FRAME.Local);
        List<Subfield> sfs = df.getSubfields("ayz");
        for (int i = 0; i < sfs.size(); i++) {
            Resource resource = resources.get(i);
            Subfield sf = sfs.get(i);
            String label = StringUtils.substringBetween(sf.getData(), "(", ")");
            resource.addProperty(BIB_FRAME.source, ModelUtils.createSource(model, label));
            instance.addProperty(BIB_FRAME.identifiedBy, resource);
        }
        return model;
    }
}