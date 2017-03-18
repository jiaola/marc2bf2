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
    private Model model;
    public Marc001To007Converter(Model model) {
        super();
        this.model = model;
    }

    public Resource convert001(ControlField field) {
        if (!field.getTag().equals("001")) {
            return null;
        }
        return model.createResource().
                addProperty(RDF.type, BIB_FRAME.Local).
                addProperty(RDF.value, field.getData());
    }

    public Resource convert003(ControlField field) {
        if (!field.getTag().equals("003")) {
            return null;
        }
        return model.createResource().
                addProperty(RDF.type, BIB_FRAME.Source).
                addProperty(BIB_FRAME.code, field.getData());
    }

    public Resource convert005(ControlField field) {
        if (!field.getTag().equals("005")) {
            return null;
        }
        Model model = ModelFactory.createBfModel();
        return model.createResource();
    }
}
