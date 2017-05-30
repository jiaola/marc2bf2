package io.lold.marc2bf2.utils;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import java.util.Arrays;

public class SubfieldUtils {
    public static Resource mapSubfield0(Model model, String value) {
        if (value.startsWith("(DE-101c)")) {
            return model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Identifier)
                    .addProperty(RDF.value, value.substring(9))
                    .addProperty(BIB_FRAME.source, model.createResource()
                            .addProperty(RDF.type, BIB_FRAME.Source)
                            .addProperty(RDFS.label,  "DE-101c"));
        } else if (value.startsWith("(isni)")) {
            return model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Identifier)
                    .addProperty(RDF.value, value.substring(6))
                    .addProperty(BIB_FRAME.source, model.createResource()
                            .addProperty(RDF.type, BIB_FRAME.Source)
                            .addProperty(RDFS.label,  "isni"));
        } else if (value.startsWith("(uri)")) {
            String uri = value.substring(5);
            return model.createResource(uri)
                    .addProperty(RDF.type, BIB_FRAME.Agent)
                    .addProperty(RDF.type, BIB_FRAME.Person);
        } else {
            String[] parts = value.split("[()]");
            parts = Arrays.stream(parts).filter(StringUtils::isNotBlank).toArray(String[]::new);
            Resource identifier = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Identifier);
            if (parts.length == 2) {
                identifier.addProperty(RDF.value, parts[1])
                        .addProperty(BIB_FRAME.source, model.createResource()
                                .addProperty(RDF.type, BIB_FRAME.Source)
                                .addProperty(RDFS.label, parts[0]));
            } else if (parts.length == 1) {
                identifier.addProperty(RDF.value, parts[0]);
            }
            return identifier;
        }
    }

    public static Resource mapSubfield2(Model model, String value) {
        return model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Source)
                .addProperty(RDFS.label, value);
    }

    public static Resource mapSubfield2(Model model, String value, String lang) {
        return model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Source)
                .addProperty(RDFS.label, value, lang);
    }

    public static Resource mapSubfield3(Model model, String value) {
        return model.createResource()
                .addProperty(RDF.type, BIB_FRAME_LC.AppliesTo)
                .addProperty(RDFS.label, FormatUtils.chopPunctuation(value));
    }

    public static Resource mapSubfield3(Model model, String value, String lang) {
        return model.createResource()
                .addProperty(RDF.type, BIB_FRAME_LC.AppliesTo)
                .addProperty(RDFS.label, FormatUtils.chopPunctuation(value), lang);
    }

    public static Resource mapSubfield5(Model model, String value) {
        return model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Agent)
                .addProperty(BIB_FRAME.code, value);
    }

}
