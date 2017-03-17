package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.ModelFactory;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.ControlField;

/**
 * Conversion Specs for MARC 001-007 fields
 */
public class Marc001To007Converter {
    public Marc001To007Converter() {
        super();
    }

    public Model convert001(ControlField field) {
        if (!field.getTag().equals("001")) {
            return null;
        }
        Model model = ModelFactory.createBfModel();
        Resource local = model.createResource().
                addProperty(RDF.type, BIB_FRAME.Local).
                addProperty(RDF.value, field.getData());
        model.createResource().
                addProperty(RDF.type, BIB_FRAME.AdminMetadata).
                addProperty(RDF.value, local);
        return model;
    }
}
