package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class SubfieldMapper {
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
}
