package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.converters.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
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

    public static Resource mapSubfield3(Model model, String value) {
        Resource source = model.createResource()
                .addProperty(RDF.type, BIB_FRAME_LC.AppliesTo)
                .addProperty(RDFS.label, RecordUtils.chopPunctuation(value));
        return source;
    }

    public static Resource mapSubfield3(Model model, String value, String lang) {
        Resource source = model.createResource()
                .addProperty(RDF.type, BIB_FRAME_LC.AppliesTo)
                .addProperty(RDFS.label, RecordUtils.chopPunctuation(value), lang);
        return source;
    }
}
