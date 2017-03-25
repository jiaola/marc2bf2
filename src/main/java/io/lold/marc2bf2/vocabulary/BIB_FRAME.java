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
    public static final Resource Carrier;
    public static final Resource Cartography;
    public static final Resource ColorContent;
    public static final Resource DigitalCharacteristic;
    public static final Resource Electronic;
    public static final Resource Emulsion;
    public static final Resource Generation;
    public static final Resource GenreForm;
    public static final Resource Instance;
    public static final Resource Local;
    public static final Resource Media;
    public static final Resource Mount;
    public static final Resource MovingImage;
    public static final Resource Note;
    public static final Resource PlaybackChannels;
    public static final Resource Polarity;
    public static final Resource ProjectionCharacteristic;
    public static final Resource ReductionRatio;
    public static final Resource SoundContent;
    public static final Resource Source;
    public static final Resource StillImage;
    public static final Resource Work;

    // ---- properties ----
    public static final Property adminMetadata;
    public static final Property baseMaterial;
    public static final Property carrier;
    public static final Property changeDate;
    public static final Property code;
    public static final Property colorContent;
    public static final Property digitalCharacteristic;
    public static final Property dimensions;
    public static final Property emulsion;
    public static final Property generation;
    public static final Property genreForm;
    public static final Property identifiedBy;
    public static final Property media;
    public static final Property mount;
    public static final Property note;
    public static final Property noteType;
    public static final Property polarity;
    public static final Property projectionCharacteristic;
    public static final Property reductionRatio;
    public static final Property soundContent;
    public static final Property soundCharacteristic;
    public static final Property source;

    public static String getURI() {
        return NAMESPACE;
    }

    static {
        AcquisitionSource = m.createResource(BIB_FRAME.NAMESPACE + "AcquisitionSource");
        AdminMetadata = m.createResource(BIB_FRAME.NAMESPACE + "AdminMetadata");
        Audio = m.createResource(BIB_FRAME.NAMESPACE + "Audio");
        BaseMaterial = m.createResource(BIB_FRAME.NAMESPACE + "BaseMaterial");
        Carrier = m.createResource(BIB_FRAME.NAMESPACE + "Carrier");
        Cartography = m.createResource(BIB_FRAME.NAMESPACE + "Cartography");
        ColorContent = m.createResource(BIB_FRAME.NAMESPACE + "ColorContent");
        DigitalCharacteristic = m.createResource(BIB_FRAME.NAMESPACE + "DigitalCharacteristic");
        Electronic = m.createResource(BIB_FRAME.NAMESPACE + "Electronic");
        Emulsion = m.createResource(BIB_FRAME.NAMESPACE + "Emulsion");
        Generation = m.createResource(BIB_FRAME.NAMESPACE + "Generation");
        GenreForm = m.createResource(BIB_FRAME.NAMESPACE + "GenreForm");
        Instance = m.createResource(BIB_FRAME.NAMESPACE + "Instance");
        Local = m.createResource(BIB_FRAME.NAMESPACE + "Local");
        Media = m.createResource(BIB_FRAME.NAMESPACE + "Media");
        Mount = m.createResource(BIB_FRAME.NAMESPACE + "Mount");
        MovingImage = m.createResource(BIB_FRAME.NAMESPACE + "MovingImage");
        Note = m.createResource(BIB_FRAME.NAMESPACE + "Note");
        PlaybackChannels = m.createResource(BIB_FRAME.NAMESPACE + "PlaybackChannels");
        Polarity = m.createResource(BIB_FRAME.NAMESPACE + "Polarity");
        ProjectionCharacteristic = m.createResource(BIB_FRAME.NAMESPACE + "ProjectionCharacteristic");
        ReductionRatio = m.createResource(BIB_FRAME.NAMESPACE + "ReductionRatio");
        SoundContent = m.createResource(BIB_FRAME.NAMESPACE + "SoundContent");
        Source = m.createResource(BIB_FRAME.NAMESPACE + "Source");
        StillImage = m.createResource(BIB_FRAME.NAMESPACE + "StillImage");
        Work = m.createResource(BIB_FRAME.NAMESPACE + "Work");

        adminMetadata = m.createProperty(BIB_FRAME.NAMESPACE, "adminMetadata");
        baseMaterial = m.createProperty(BIB_FRAME.NAMESPACE, "baseMaterial");
        carrier = m.createProperty(BIB_FRAME.NAMESPACE, "carrier");
        changeDate = m.createProperty(BIB_FRAME.NAMESPACE, "changeDate");
        code = m.createProperty(BIB_FRAME.NAMESPACE, "code");
        colorContent = m.createProperty(BIB_FRAME.NAMESPACE, "colorContent");
        digitalCharacteristic = m.createProperty(BIB_FRAME.NAMESPACE, "digitalCharacteristic");
        dimensions = m.createProperty(BIB_FRAME.NAMESPACE, "dimensions");
        emulsion = m.createProperty(BIB_FRAME.NAMESPACE, "emulsion");
        generation = m.createProperty(BIB_FRAME.NAMESPACE, "generation");
        genreForm = m.createProperty(BIB_FRAME.NAMESPACE, "genreForm");
        identifiedBy = m.createProperty(BIB_FRAME.NAMESPACE, "identifiedBy");
        media = m.createProperty(BIB_FRAME.NAMESPACE, "media");
        mount = m.createProperty(BIB_FRAME.NAMESPACE, "mount");
        note = m.createProperty(BIB_FRAME.NAMESPACE, "note");
        noteType = m.createProperty(BIB_FRAME.NAMESPACE, "noteType");
        polarity = m.createProperty(BIB_FRAME.NAMESPACE, "polarity");
        projectionCharacteristic = m.createProperty(BIB_FRAME.NAMESPACE, "projectionCharacteristic");
        reductionRatio = m.createProperty(BIB_FRAME.NAMESPACE, "reductionRatio");
        soundContent = m.createProperty(BIB_FRAME.NAMESPACE, "soundContent");
        soundCharacteristic = m.createProperty(BIB_FRAME.NAMESPACE, "soundCharacteristic");
        source = m.createProperty(BIB_FRAME.NAMESPACE, "source");
    }
}
