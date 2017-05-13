package io.lold.marc2bf2.vocabulary;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

/**
 * Created by djiao on 3/16/17.
 */
public class MADS_RDF {
    private static final Model m = ModelFactory.createDefaultModel();
    public static final String NAMESPACE = "http://www.loc.gov/mads/rdf/v1#";
    public static final String PREFIX = "madsrdf";

    public static String getURI() {
        return NAMESPACE;
    }

    // ---- classes ----
    public static final Resource Address;
    public static final Resource Title;
    public static final Resource ComplexSubject;
    public static final Resource NameTitle;
    public static final Resource CorporateName;
    public static final Resource Name;
    public static final Resource ConferenceName;

    // ---- properties ----
    public static final Property authoritativeLabel;
    public static final Property code;
    public static final Property hasTopMemberOfMADSScheme;
    public static final Property isMemberofMADSScheme;

    
    static {
        Address = m.createResource(NAMESPACE + "Address");
        ConferenceName = m.createResource(NAMESPACE + "ConferenceName");
        Name = m.createResource(NAMESPACE + "Name");
        Title = m.createResource(NAMESPACE + "Title");
        NameTitle = m.createResource(NAMESPACE + "NameTitle");
        CorporateName = m.createResource(NAMESPACE + "CorporateName");
        ComplexSubject = m.createResource(NAMESPACE + "ComplexSubject");

        authoritativeLabel = m.createProperty(NAMESPACE, "authoritativeLabel");
        code = m.createProperty(NAMESPACE, "code");
        hasTopMemberOfMADSScheme = m.createProperty(NAMESPACE, "hasTopMemberOfMADSScheme");
        isMemberofMADSScheme = m.createProperty(NAMESPACE, "isMemberofMADSScheme");
    }
}
