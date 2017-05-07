package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.ModelFactory;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.utils.SubfieldUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.impl.XSDDateType;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class FieldConverter {
    protected Model model;
    protected Record record;
    protected int fieldIndex = 0;

    public FieldConverter(Model model, Record record) {
        this.model = model;
        this.record = record;
    }

    public abstract Model convert(VariableField field) throws Exception;

    public void setFieldIndex (int index) {
        this.fieldIndex = index;
    }

    protected void addSubfield0(DataField field, Resource resource) {
        addSubfield0AndW(field.getSubfields('0'), resource);
    }

    protected void addSubfield0(Subfield sf, Resource resource) {
        addSubfield0AndW(Arrays.asList(sf), resource);
    }

    protected void addSubfieldW(DataField field, Resource resource) {
        addSubfield0AndW(field.getSubfields('w'), resource);
    }

    protected void addSubfield0AndW(List<Subfield> subfields, Resource resource) {
        for (Subfield sf0: subfields) {
            String[] parts = sf0.getData().split("[\\(\\)]");
            parts = Arrays.stream(parts).filter(p -> StringUtils.isNotBlank(p)).toArray(String[]::new);
            Resource identifier = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Identifier);
            if (parts.length == 2) {
                identifier.addProperty(RDF.value, parts[1])
                        .addProperty(BIB_FRAME.source, createLabeledResource(BIB_FRAME.Source, parts[0]));
            } else if (parts.length == 1) {
                identifier.addProperty(RDF.value, parts[0]);
            } else {
                // TODO: Throws a warning
            }
            resource.addProperty(BIB_FRAME.identifiedBy, identifier);
        }
    }

    protected void addSubfield2(DataField field, Resource resource) {
        for (Subfield sf2: field.getSubfields('2')) {
            resource.addProperty(BIB_FRAME.source, SubfieldUtils.mapSubfield2(model, sf2.getData()));
        }
    }

    protected void addSubfield5(DataField field, Resource resource) {
        for (Subfield sf5: field.getSubfields('5')) {
            Resource agent = SubfieldUtils.mapSubfield5(model, sf5.getData());
            resource.addProperty(BIB_FRAME_LC.applicableInstitution, agent);
        }
    }

    protected void addSubfield3(DataField field, Resource resource) {
        for (Subfield sf3: field.getSubfields('3')) {
            Resource appliesTo = SubfieldUtils.mapSubfield3(model, sf3.getData());
            resource.addProperty(BIB_FRAME_LC.appliesTo, appliesTo);
        }
    }

    protected void addSubfield7(DataField field, Resource resource) {
        for (Subfield sf7: field.getSubfields('7')) {
            String first = StringUtils.substring(sf7.getData(), 0, 1);
            String second = StringUtils.substring(sf7.getData(), 1, 2);
            Resource type = null;
            switch (first) {
                case "a": type = BIB_FRAME.Text; break;
                case "c": type = BIB_FRAME.NotatedMusic; break;
                case "d": type = BIB_FRAME.NotatedMusic; break;
                case "e": type = BIB_FRAME.Cartography; break;
                case "f": type = BIB_FRAME.Cartography; break;
                case "g": type = BIB_FRAME.MovingImage; break;
                case "i": type = BIB_FRAME.Audio; break;
                case "j": type = BIB_FRAME.Audio; break;
                case "k": type = BIB_FRAME.StillImage; break;
                case "o": type = BIB_FRAME.MixedMaterial; break;
                case "p": type = BIB_FRAME.MixedMaterial; break;
                case "r": type = BIB_FRAME.Object; break;
                case "t": type = BIB_FRAME.Text; break;
                default: break;
            }
            if (type != null) {
                resource.addProperty(RDF.type, type);
            }
            String issuance = null;
            switch (second) {
                case "a": issuance = "m"; break;
                case "b": issuance = "s"; break;
                case "d": issuance = "d"; break;
                case "i": issuance = "i"; break;
                case "m": issuance = "m"; break;
                case "s": issuance = "s"; break;
                default: break;
            }
            if (issuance != null) {
                resource.addProperty(BIB_FRAME.issuance, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Issuance)
                        .addProperty(BIB_FRAME.code, issuance));
            }
        }
    }

    protected void addSubfieldU(DataField field, Resource resource) {
        for (Subfield sfu: field.getSubfields('u')) {
            resource.addProperty(RDFS.label, model.createTypedLiteral(sfu.getData(), XSDDateType.XSDanyURI));
        }
    }

    protected Literal createLiteral(String lang, Subfield subfield) {
        return StringUtils.isBlank(lang) ?
                model.createLiteral(subfield.getData(), lang) :
                model.createLiteral(subfield.getData());
    }

    protected Literal createLiteral(Subfield subfield) {
        return createLiteral(null, subfield);
    }

    protected Literal createLiteral(String lang, String value) {
        return StringUtils.isBlank(lang) ?
                model.createLiteral(value, lang) :
                model.createLiteral(value);
    }

    protected String concatSubfields(DataField field, String subfields, String separator) {
        List<Subfield> sfs = field.getSubfields(subfields);
        List<String> values = sfs.stream().map(sf -> sf.getData().trim()).collect(Collectors.toList());
        return StringUtils.join(values, separator);
    }

    protected List<Resource> contributionRole(List<Subfield> sfs, String lang) {
        List<Resource> resources = new ArrayList<>();
        for (Subfield sf: sfs) {
            String[] roles = sf.getData().split(",|\\sand|&amp;");
            for (String role: roles) {
                if (StringUtils.isNotBlank(role)) {
                    resources.add(createLabeledResource(BIB_FRAME.Role, role, lang));
                }
            }
        }
        return resources;
    }

    protected List<Resource> contributionRelationship(List<Subfield> sfs, String lang, String relatedTo) {
        List<Resource> resources = new ArrayList<>();
        for (Subfield sf: sfs) {
            String[] relationships = sf.getData().split(",|\\sand|&amp;");
            for (String relationship: relationships) {
                if (StringUtils.isNotBlank(relationship)) {
                    Resource resource = createRelationship(relationship, relatedTo, lang);
                    resources.add(resource);
                }
            }
        }
        return resources;

    }

    public Resource createLabeledResource(Resource type, String label) {
        return model.createResource()
                .addProperty(RDF.type, type)
                .addProperty(RDFS.label, label);
    }

    public Resource createLabeledResource(Resource type, String label, String lang) {
        return model.createResource()
                .addProperty(RDF.type, type)
                .addProperty(RDFS.label, createLiteral(lang, label));
    }

    protected List<Resource> contributionRoleCode(List<Subfield> sfs) {
        List<Resource> resources = new ArrayList<>();
        for (Subfield sf: sfs) {
            String uri = ModelUtils.getUriWithNsPrefix("relators",
                    StringUtils.substring(sf.getData(), 0, 3));
            Resource resource = model.createResource(uri)
                    .addProperty(RDF.type, BIB_FRAME.Role);
            resources.add(resource);
        }
        return resources;
    }

    protected Resource createRelationship(String label, String relatedTo, String lang) {
        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME_LC.Relationship)
                .addProperty(BIB_FRAME_LC.relation, model.createResource()
                        .addProperty(RDF.type, RDFS.Resource)
                        .addProperty(RDFS.label, createLiteral(lang, label)));
        if (StringUtils.isNotBlank(relatedTo)) {
            resource.addProperty(BIB_FRAME.relatedTo, model.createResource(relatedTo));
        }
        return resource;
    }

    protected Resource createRelationship(String relator, String relatedTo) {
        String uri = ModelUtils.getUriWithNsPrefix("relators", relator);
        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME_LC.Relationship)
                .addProperty(BIB_FRAME_LC.relation, model.createResource(uri)
                        .addProperty(RDF.type, RDFS.Resource));
        if (StringUtils.isNotBlank(relatedTo)) {
            resource.addProperty(BIB_FRAME.relatedTo, model.createResource(relatedTo));
        }
        return resource;
    }

    /**
     * Get the tag of the field. For 880, it returns the value in $6
     * @param field
     * @return
     */
    protected String getTag(DataField field) {
        return "880".equals(field.getTag()) ?
                StringUtils.substring(field.getSubfieldsAsString("6"), 0, 3) :
                field.getTag();
    }

    protected String generateFieldUri(DataField field, Resource resource) {
        List<Subfield> subfields = field.getSubfields();
        Subfield sf0orw = null;
        if (field.getSubfield('t') != null) {
            for (int i = 0; i < subfields.size(); i++) {
                Subfield sf = subfields.get(i);
                if (sf.getCode() == 't') {
                    if ("Work".equals(resource.getLocalName())) {
                        sf0orw = Optional.ofNullable(RecordUtils.lookBack(field, i, i, '0'))
                                .orElse(RecordUtils.lookBack(field, i, i, 'w'));
                    } else if ("Agent".equals(resource.getLocalName())) {
                        sf0orw = Optional.ofNullable(RecordUtils.lookAhead(field, i, subfields.size(), '0'))
                                .orElse(RecordUtils.lookAhead(field, i, subfields.size(), 'w'));
                    }
                }
            }
        } else {
            sf0orw = Optional.ofNullable(field.getSubfield('0'))
                    .orElse(field.getSubfield('w'));
        }
        if (sf0orw != null) {
            if (sf0orw.getData().startsWith("(uri)")) {
                return StringUtils.substringAfter(sf0orw.getData(), "(uri)");
            } else if (sf0orw.getData().startsWith("http")) {
                return sf0orw.getData();
            }
        }
        return null;
    }
}
