package io.lold.marc2bf2.converters.field3xx;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field340Converter extends FieldConverter {
    public Field340Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("340")) {
            return model;
        }
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        for (Subfield sf: df.getSubfields('b')) {
            instance.addProperty(BIB_FRAME.dimensions, sf.getData());
        }

        processSubfield(instance, df, 'a', BIB_FRAME.baseMaterial, BIB_FRAME.BaseMaterial);
        processSubfield(instance, df, 'c', BIB_FRAME.appliedMaterial, BIB_FRAME.AppliedMaterial);
        processSubfield(instance, df, 'd', BIB_FRAME.productionMethod, BIB_FRAME.ProductionMethod);
        processSubfield(instance, df, 'e', BIB_FRAME.mount, BIB_FRAME.Mount);
        processSubfield(instance, df, 'f', BIB_FRAME.reductionRatio, BIB_FRAME.ReductionRatio);
        processSubfield(instance, df, 'i', BIB_FRAME.systemRequirement, BIB_FRAME.SystemRequirement);
        processSubfield(instance, df, 'j', BIB_FRAME.generation, BIB_FRAME.Generation);
        processSubfield(instance, df, 'k', BIB_FRAME.layout, BIB_FRAME.Layout);
        processSubfield(instance, df, 'm', BIB_FRAME.bookFormat, BIB_FRAME.BookFormat);
        processSubfield(instance, df, 'n', BIB_FRAME.fontSize, BIB_FRAME.FontSize);
        processSubfield(instance, df, 'o', BIB_FRAME.polarity, BIB_FRAME.Polarity);

        return model;
    }

    private void processSubfield(Resource instance, DataField field, char code, Property property, Resource type) {
        String lang = RecordUtils.getXmlLang(field, record);
        for (Subfield sf: field.getSubfields(code)) {
            Resource resource = createLabeledResource(type, sf.getData(), lang);
            addSubfield2(field, resource);
            instance.addProperty(property, resource);
        }
    }
}
