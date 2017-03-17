package io.lold.marc2bf2.vocabulary;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

/**
 * Created by djiao on 3/16/17.
 */
public class BIB_FRAME {
    private static final Model m = ModelFactory.createDefaultModel();
    public static final java.lang.String NAMESPACE = "http://id.loc.gov/ontologies/bibframe/";
    public static final java.lang.String PREFIX = "bf";

    // ---- classes ----
    public static final Resource AcquisitionSource;
    public static final Resource AdminMetadata;
    public static final Resource Local;
    public static final Resource Work;

    // ---- properties ----
    public static final Property identifiedBy;

    public static String getURI() {
        return NAMESPACE;
    }

    static {
        AcquisitionSource = m.createResource(BIB_FRAME.NAMESPACE + "AcquisitionSource");
        AdminMetadata = m.createResource(BIB_FRAME.NAMESPACE +"AdminMetadata");
        Local = m.createResource(BIB_FRAME.NAMESPACE +"Local");
        Work = m.createResource(BIB_FRAME.NAMESPACE +"Work");

        identifiedBy = m.createProperty(BIB_FRAME.NAMESPACE, "identifiedBy");
    }
}
