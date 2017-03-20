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
    public static final Resource BaseMaterial;
    public static final Resource Audio;
    public static final Resource Cartography;
    public static final Resource ColorContent;
    public static final Resource GenreForm;
    public static final Resource Local;
    public static final Resource MovingImage;
    public static final Resource Source;
    public static final Resource StillImage;
    public static final Resource Work;

    // ---- properties ----
    public static final Property adminMetadata;
    public static final Property baseMaterial;
    public static final Property changeDate;
    public static final Property code;
    public static final Property colorContent;
    public static final Property genreForm;
    public static final Property identifiedBy;
    public static final Property source;

    public static String getURI() {
        return NAMESPACE;
    }

    static {
        AcquisitionSource = m.createResource(BIB_FRAME.NAMESPACE + "AcquisitionSource");
        AdminMetadata = m.createResource(BIB_FRAME.NAMESPACE + "AdminMetadata");
        Audio = m.createResource(BIB_FRAME.NAMESPACE + "Audio");
        BaseMaterial = m.createResource(BIB_FRAME.NAMESPACE + "BaseMaterial");
        Cartography = m.createResource(BIB_FRAME.NAMESPACE + "Cartography");
        ColorContent = m.createResource(BIB_FRAME.NAMESPACE + "ColorContent");
        GenreForm = m.createResource(BIB_FRAME.NAMESPACE + "GenreForm");
        Local = m.createResource(BIB_FRAME.NAMESPACE + "Local");
        MovingImage = m.createResource(BIB_FRAME.NAMESPACE + "MovingImage");
        Source = m.createResource(BIB_FRAME.NAMESPACE + "Source");
        StillImage = m.createResource(BIB_FRAME.NAMESPACE + "StillImage");
        Work = m.createResource(BIB_FRAME.NAMESPACE + "Work");

        adminMetadata = m.createProperty(BIB_FRAME.NAMESPACE, "adminMetadata");
        baseMaterial = m.createProperty(BIB_FRAME.NAMESPACE, "baseMaterial");
        changeDate = m.createProperty(BIB_FRAME.NAMESPACE, "changeDate");
        code = m.createProperty(BIB_FRAME.NAMESPACE, "code");
        colorContent = m.createProperty(BIB_FRAME.NAMESPACE, "colorContent");
        genreForm = m.createProperty(BIB_FRAME.NAMESPACE, "genreForm");
        identifiedBy = m.createProperty(BIB_FRAME.NAMESPACE, "identifiedBy");
        source = m.createProperty(BIB_FRAME.NAMESPACE, "source");
    }
}
