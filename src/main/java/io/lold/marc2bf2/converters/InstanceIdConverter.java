package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class InstanceIdConverter extends FieldConverter {
    public InstanceIdConverter(Model model, Record record) {
        super(model, record);
    }

    public List<Resource> convert(VariableField field, Resource identifier, String invalidLabel, boolean chopPunct) throws Exception {
        DataField df = (DataField) field;
        List<Resource> resources = new ArrayList<>();
        for (Subfield sf: df.getSubfields("ayz")) {
            String data = sf.getData();
            String vid = "035".equals(field.getTag()) ?
                StringUtils.substringAfter(data, ")") : data;
            if (chopPunct) vid = FormatUtils.chopPunctuation(vid);
            Resource resource = model.createResource()
                    .addProperty(RDF.type, identifier)
                    .addProperty(RDF.value, vid);
            if (sf.getCode() == 'z') {
                resource.addProperty(BIB_FRAME.status, ModelUtils.createLabeledResource(model, invalidLabel, BIB_FRAME.Status));
            }
            if (sf.getCode() == 'y') {
                resource.addProperty(BIB_FRAME.status, ModelUtils.createLabeledResource(model, "incorrect", BIB_FRAME.Status));
            }
            for (Subfield c: df.getSubfields('c')) {
                String value = FormatUtils.chopPunctuation(c.getData(), ":,;/\\s");
                resource.addProperty(BIB_FRAME.acquisitionTerms, value);
            }
            for (Subfield q: df.getSubfields('q')) {
                String value = FormatUtils.chopPunctuation(q.getData(), ":,;/\\s");
                resource.addProperty(BIB_FRAME.qualifier, value);
            }

            if ("024".equals(field.getTag()) && sf.getCode() == 'a') {
                for (Subfield d: ((DataField) field).getSubfields('d')) {
                    Resource note = model.createResource()
                            .addProperty(RDF.type, BIB_FRAME.Note)
                            .addProperty(BIB_FRAME.noteType, "additional codes")
                            .addProperty(RDFS.label, d.getData());
                    resource.addProperty(BIB_FRAME.note, note);
                }
            }
            resources.add(resource);
        }
        return resources;
    }

    public List<Resource> convert(VariableField field, Resource identifier, String invalidLabel) throws Exception {
        return convert(field, identifier, invalidLabel, false);
    }

    public List<Resource> convert(VariableField field, Resource identifier) throws Exception {
        return convert(field, identifier, "invalid", false);
    }

    public List<Resource> convertSubfieldB(DataField field) {
        return field.getSubfields('b').stream()
                .map(b -> ModelUtils.createSource(model, FormatUtils.chopPunctuation(b.getData())))
                .collect(Collectors.toList());
    }


    protected void addSubfield2(DataField field, Resource resource) {
        for (Subfield sf2: field.getSubfields('2')) {
            Resource source = SubfieldUtils.mapSubfield2(model, sf2.getData());
            resource.addProperty(BIB_FRAME.source, source);
        }
    }
}
