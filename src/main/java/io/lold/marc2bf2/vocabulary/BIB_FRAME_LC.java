package io.lold.marc2bf2.vocabulary;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class BIB_FRAME_LC {
    private static final Model m = ModelFactory.createDefaultModel();

    public static final String NAMESPACE = "http://id.loc.gov/ontologies/bflc/";
    public static final String PREFIX = "bflc";

    public static String getURI() {
        return NAMESPACE;
    }

    public static final Resource MetadataLicensor;
    
    public static final Property metadataLicensor;

    static {
        MetadataLicensor = m.createResource(NAMESPACE + "MetadataLicensor");

        metadataLicensor = m.createProperty(NAMESPACE, "metadataLicensor");
    }

}
