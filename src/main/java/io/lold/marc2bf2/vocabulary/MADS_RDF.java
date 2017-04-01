package io.lold.marc2bf2.vocabulary;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;

/**
 * Created by djiao on 3/16/17.
 */
public class MADS_RDF {
    private static final Model m = ModelFactory.createDefaultModel();
    public static final java.lang.String NAMESPACE = "http://www.loc.gov/mads/rdf/v1#";
    public static final java.lang.String PREFIX = "madsrdf";

    // ---- classes ----


    // ---- properties ----
    public static final Property code;
    public static final Property hasTopMemberOfMADSScheme;
    
    static {
        code = m.createProperty(MADS_RDF.NAMESPACE, "code");
        hasTopMemberOfMADSScheme = m.createProperty(MADS_RDF.NAMESPACE, "hasTopMemberOfMADSScheme");
    }
}
