package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.datatypes.xsd.impl.XSDDateType;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

public class Field520Converter extends FieldConverter {
    public Field520Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);


        String label = concatSubfields(df, "ab", " ");
        if (StringUtils.isNotBlank(label)) {
            Resource resource = createLabeledResource(BIB_FRAME.Summary, label, lang);
            for (Subfield sf: df.getSubfields('u')) {
                Resource source = model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Source)
                        .addProperty(RDFS.label, model.createTypedLiteral(sf.getData(), XSDDateType.XSDanyURI));
                resource.addProperty(BIB_FRAME.source, source);
            }
            addSubfieldU(df, resource);
            for (Subfield sf: df.getSubfields('c')) {
                resource.addProperty(BIB_FRAME.source, SubfieldUtils.mapSubfield2(model, sf.getData()));
            }
            addSubfield3(df, resource);
            work.addProperty(BIB_FRAME.summary, resource);
        }
        return model;
    }


    @Override
    public boolean checkField(VariableField field) {
        return "520".equals(field.getTag());
    }
}
