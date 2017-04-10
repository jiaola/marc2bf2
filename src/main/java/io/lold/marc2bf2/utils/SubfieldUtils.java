package io.lold.marc2bf2.utils;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class SubfieldUtils {
    public static Resource mapSubfield0(Model model, String value) {
        if (value.startsWith("(DE-101c)")) {
            return model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Identifier)
                    .addProperty(RDF.value, value.substring(9))
                    .addProperty(BIB_FRAME.source, ModelUtils.createSource(model, "DE-101c"));
        } else if (value.startsWith("(isni)")) {
            return model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Identifier)
                    .addProperty(RDF.value, value.substring(6))
                    .addProperty(BIB_FRAME.source, ModelUtils.createSource(model, "isni"));
        } else if (value.startsWith("(uri)")) {
            String uri = value.substring(5);
            return model.createResource(uri)
                    .addProperty(RDF.type, BIB_FRAME.Agent)
                    .addProperty(RDF.type, BIB_FRAME.Person);
        } else {
            return null;
        }
    }

    public static Resource mapSubfield2(Model model, String value) {
        Resource source = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Source)
                .addProperty(RDFS.label, value);
        return source;
    }

    public static Resource mapSubfield2(Model model, String value, String lang) {
        Resource source = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Source)
                .addProperty(RDFS.label, value, lang);
        return source;
    }

    public static Resource mapSubfield3(Model model, String value) {
        Resource source = model.createResource()
                .addProperty(RDF.type, BIB_FRAME_LC.AppliesTo)
                .addProperty(RDFS.label, FormatUtils.chopPunctuation(value));
        return source;
    }

    public static Resource mapSubfield3(Model model, String value, String lang) {
        Resource source = model.createResource()
                .addProperty(RDF.type, BIB_FRAME_LC.AppliesTo)
                .addProperty(RDFS.label, FormatUtils.chopPunctuation(value), lang);
        return source;
    }
}
