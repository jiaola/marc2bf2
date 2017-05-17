package io.lold.marc2bf2.converters.field3XX;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field385Converter extends FieldConverter {
    public Field385Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource work = ModelUtils.getWork(model, record);

        Property property = "385".equals(getTag(df)) ?
                BIB_FRAME.intendedAudience :
                BIB_FRAME_LC.creatorCharacteristic;

        String lang = RecordUtils.getXmlLang(df, record);
        List<Subfield> sfs = df.getSubfields();
        for (int i = 0; i < sfs.size(); i++) {
            Subfield sf = sfs.get(i);
            if (sf.getCode() != 'a') continue;
            Resource resource = "385".equals(getTag(df)) ?
                model.createResource().addProperty(RDF.type, BIB_FRAME.IntendedAudience) :
                model.createResource().addProperty(RDF.type, BIB_FRAME_LC.CreatorCharacteristic);
            resource.addProperty(RDFS.label, createLiteral(sf.getData(), lang));
            Subfield sfb = RecordUtils.lookAhead(df, i, sfs.size(), 'b');
            if (sfb != null) {
                resource.addProperty(BIB_FRAME.code, sfb.getData());
                Subfield sf0 = df.getSubfield('0');
                if (sf0 != null) {
                    String value = StringUtils.substringAfter(sf0.getData(), ")");
                    if (value.startsWith("dg")) {
                        String uri = ModelUtils.getUriWithNsPrefix("demographicTerms", value);
                        resource.addProperty(BIB_FRAME_LC.demographicGroup, model.createResource(uri));
                    }
                }
            }
            for (Subfield sfmn: df.getSubfields("mn")) {
                resource.addProperty(BIB_FRAME_LC.demographicGroup,
                        createLabeledResource(BIB_FRAME_LC.DemographicGroup, sfmn.getData(), lang));
            }
            addSubfield2(df, resource);
            addSubfield3(df, resource);
            work.addProperty(property, resource);
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "385".equals(field.getTag());
    }
}
