package io.lold.marc2bf2.converters.field760to788;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field762Converter extends Field760Converter {
    public Field762Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;

        Resource resource = createWork(df);
        resource.addProperty(BIB_FRAME.hasInstance, createInstance(df).addProperty(BIB_FRAME.instanceOf, resource));

        Resource instance = ModelUtils.getInstance(model, record);
        instance.addProperty(BIB_FRAME.hasSubseries, resource);
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "762".equals(field.getTag());
    }

}
