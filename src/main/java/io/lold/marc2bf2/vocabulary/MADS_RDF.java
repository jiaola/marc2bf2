package io.lold.marc2bf2.vocabulary;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

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
    public static final Resource Temporal;
    public static final Resource GenreForm;
    public static final Resource Topic;
    public static final Resource Geographic;
    public static final Resource Occupation;
    public static final Resource HierarchicalGeographic;
    public static final Resource County;
    public static final Resource Country;
    public static final Resource City;
    public static final Resource State;
    public static final Resource Region;
    public static final Resource CitySection;
    public static final Resource ExtraterrestrialArea;

    // ---- properties ----
    public static final Property authoritativeLabel;
    public static final Property code;
    public static final Property hasTopMemberOfMADSScheme;
    public static final Property isMemberofMADSScheme;
    public static final Property componentList;

    
    static {
        Address = m.createResource(NAMESPACE + "Address");
        ConferenceName = m.createResource(NAMESPACE + "ConferenceName");
        Name = m.createResource(NAMESPACE + "Name");
        Title = m.createResource(NAMESPACE + "Title");
        NameTitle = m.createResource(NAMESPACE + "NameTitle");
        CorporateName = m.createResource(NAMESPACE + "CorporateName");
        ComplexSubject = m.createResource(NAMESPACE + "ComplexSubject");
        Temporal = m.createResource(NAMESPACE + "Temporal");
        GenreForm = m.createResource(NAMESPACE + "GenreForm");
        Topic = m.createResource(NAMESPACE + "Topic");
        Geographic = m.createResource(NAMESPACE + "Geographic");
        Occupation = m.createResource(NAMESPACE + "Occupation");
        HierarchicalGeographic = m.createResource(NAMESPACE + "HierarchicalGeographic");
        County = m.createResource(NAMESPACE + "County");
        Country = m.createResource(NAMESPACE + "Country");
        City = m.createResource(NAMESPACE + "City");
        State = m.createResource(NAMESPACE + "State");
        Region = m.createResource(NAMESPACE + "Region");
        CitySection = m.createResource(NAMESPACE + "CitySection");
        ExtraterrestrialArea = m.createResource(NAMESPACE + "ExtraterrestrialArea");

        authoritativeLabel = m.createProperty(NAMESPACE, "authoritativeLabel");
        code = m.createProperty(NAMESPACE, "code");
        hasTopMemberOfMADSScheme = m.createProperty(NAMESPACE, "hasTopMemberOfMADSScheme");
        isMemberofMADSScheme = m.createProperty(NAMESPACE, "isMemberofMADSScheme");
        componentList = m.createProperty(NAMESPACE, "componentList");
    }
}
