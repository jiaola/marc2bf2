package io.lold.marc2bf2.vocabulary;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class BIB_FRAME {
    private static final Model m = ModelFactory.createDefaultModel();
    public static final java.lang.String NAMESPACE = "http://id.loc.gov/ontologies/bibframe/";
    public static final java.lang.String PREFIX = "bf";

    // ---- classes ----
    public static final Resource AcquisitionSource;
    public static final Resource AdminMetadata;
    public static final Resource Local;
    public static final Resource Source;
    public static final Resource Work;

    // ---- properties ----
    public static final Property adminMetadata;
    public static final Property code;
    public static final Property identifiedBy;
    public static final Property source;

    public static String getURI() {
        return NAMESPACE;
    }

    static {
        AcquisitionSource = m.createResource(BIB_FRAME.NAMESPACE + "AcquisitionSource");
        AdminMetadata = m.createResource(BIB_FRAME.NAMESPACE + "AdminMetadata");
        Local = m.createResource(BIB_FRAME.NAMESPACE + "Local");
        Source = m.createResource(BIB_FRAME.NAMESPACE + "Source");
        Work = m.createResource(BIB_FRAME.NAMESPACE + "Work");

        adminMetadata = m.createProperty(BIB_FRAME.NAMESPACE, "adminMetadata");
        code = m.createProperty(BIB_FRAME.NAMESPACE, "code");
        identifiedBy = m.createProperty(BIB_FRAME.NAMESPACE, "identifiedBy");
        source = m.createProperty(BIB_FRAME.NAMESPACE, "source");
    }
}
