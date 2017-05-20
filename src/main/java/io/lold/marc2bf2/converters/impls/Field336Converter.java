package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Handles RDA field 336, 337, 338
 */
public class Field336Converter extends FieldConverter {
    public Field336Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        List<Resource> list = buildRDATypes((DataField) field);
        Resource node = "336".equals(field.getTag())?
                ModelUtils.getWork(model, record) :
                ModelUtils.getInstance(model, record);
        for (Resource resource: list) {
            node.addProperty(getRDAProperty(field.getTag()), resource);
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "336".equals(field.getTag());
    }

    protected List<Resource> buildRDATypes(DataField field) {
        List<Subfield> sfs = field.getSubfields();
        String lang = RecordUtils.getXmlLang(field, record);
        List<Resource> resources = new ArrayList<>();
        for (int i = 0; i < sfs.size(); i++) {
            Subfield sf = sfs.get(i);
            if (sf.getCode() == 'a') {
                if (i < sfs.size()-1 && sfs.get(i+1).getCode() != 'b') {
                    Resource resource = model.createResource()
                            .addProperty(RDF.type, getRDAResource(field.getTag()));
                    String value = sf.getData();
                    resource.addProperty(RDFS.label, createLiteral(value, lang));
                    if (i < sfs.size()-1 && sfs.get(i+1).getCode() == '0') {
                        addSubfield0(sfs.get(i+1), resource);
                    }
                    for (Subfield two: field.getSubfields('2')) {
                        if (StringUtils.contains(two.getData(), "rda")) {
                            resource.addProperty(BIB_FRAME.source, createLabeledResource(BIB_FRAME.Source, "rda"));
                        } else {
                            resource.addProperty(BIB_FRAME.source, SubfieldUtils.mapSubfield2(model, two.getData()));
                        }
                    }
                    addSubfield3(field, resource);
                    resources.add(resource);
                }
            } else if (sf.getCode() == 'b') {
                Resource resource = model.createResource(getUriWithPrefix(getTag(field), sf.getData()))
                        .addProperty(RDF.type, getRDAResource(field.getTag()));
                if (i > 0 && sfs.get(i-1).getCode() == 'a') {
                    String value = sfs.get(i-1).getData();
                    resource.addProperty(RDFS.label, createLiteral(value, lang));
                }
                if (i < sfs.size()-1 && sfs.get(i+1).getCode() == '0') {
                    addSubfield0(sfs.get(i+1), resource);
                }
                addSubfield2(field, resource);
                addSubfield3(field, resource);
                resources.add(resource);
            }
        }
        return resources;
    }

    protected Resource getRDAResource(String tag) {
        switch (tag) {
            case "336": return BIB_FRAME.Content;
            case "337": return BIB_FRAME.Media;
            case "338": return BIB_FRAME.Carrier;
            default: return null;
        }
    }

    protected Property getRDAProperty(String tag) {
        switch (tag) {
            case "336": return BIB_FRAME.content;
            case "337": return BIB_FRAME.media;
            case "338": return BIB_FRAME.carrier;
            default: return null;
        }
    }

    protected String getUriWithPrefix(String tag, String value) {
        switch (tag) {
            case "336": return ModelUtils.getUriWithNsPrefix("contentType", value);
            case "337": return ModelUtils.getUriWithNsPrefix("mediaType", value);
            case "338": return ModelUtils.getUriWithNsPrefix("carrier", value);
            default: return null;
        }
    }

}
