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

    public static final Resource AppliesTo;
    public static final Resource EncodingLevel;
    public static final Resource MetadataLicensor;
    public static final Resource PrimaryContribution;
    public static final Resource Relation;
    public static final Resource Relationship;
    public static final Resource DemographicGroup;
    public static final Resource CreatorCharacteristic;
    public static final Resource MachineModel;
    public static final Resource ProgrammingLanguage;
    public static final Resource OperatingSystem;
    public static final Resource ImageBitDepth;

    public static final Property demographicGroup;
    public static final Property creatorCharacteristic;
    public static final Property projectedProvisionDate;
    public static final Property metadataLicensor;
    public static final Property encodingLevel;
    public static final Property appliesTo;
    public static final Property applicableInstitution;
    public static final Property relationship;
    public static final Property relation;
    public static final Property name00MatchKey;
    public static final Property name10MatchKey;
    public static final Property name11MatchKey;
    public static final Property name00MarcKey;
    public static final Property name10MarcKey;
    public static final Property name11MarcKey;
    public static final Property primaryContributorName00MatchKey;
    public static final Property primaryContributorName10MatchKey;
    public static final Property primaryContributorName11MatchKey;
    public static final Property title00MatchKey;
    public static final Property title10MatchKey;
    public static final Property title11MatchKey;
    public static final Property title30MatchKey;
    public static final Property title40MatchKey;
    public static final Property title00MarcKey;
    public static final Property title10MarcKey;
    public static final Property title11MarcKey;
    public static final Property title30MarcKey;
    public static final Property title40MarcKey;
    public static final Property titleSortKey;

    //TODO: This needs to be added to the official ontology
    public static final Property locator;

    static {
        AppliesTo = m.createResource(NAMESPACE + "AppliesTo");
        EncodingLevel = m.createResource(NAMESPACE + "EncodingLevel");
        MetadataLicensor = m.createResource(NAMESPACE + "MetadataLicensor");
        PrimaryContribution = m.createResource(NAMESPACE + "PrimaryContribution");
        Relation = m.createResource(NAMESPACE + "Relation");
        Relationship = m.createResource(NAMESPACE + "Relationship");
        DemographicGroup = m.createResource(NAMESPACE + "DemographicGroup");
        CreatorCharacteristic = m.createResource(NAMESPACE + "CreatorCharacteristic");
        MachineModel = m.createResource(NAMESPACE + "MachineModel");
        ProgrammingLanguage = m.createResource(NAMESPACE + "ProgrammingLanguage");
        OperatingSystem = m.createResource(NAMESPACE + "OperatingSystem");
        ImageBitDepth = m.createResource(NAMESPACE + "ImageBitDepth");

        demographicGroup = m.createProperty(NAMESPACE, "demographicGroup");
        creatorCharacteristic = m.createProperty(NAMESPACE, "creatorCharacteristic");
        projectedProvisionDate = m.createProperty(NAMESPACE, "projectedProvisionDate");
        metadataLicensor = m.createProperty(NAMESPACE, "metadataLicensor");
        encodingLevel = m.createProperty(NAMESPACE, "encodingLevel");
        appliesTo = m.createProperty(NAMESPACE, "appliesTo");
        applicableInstitution = m.createProperty(NAMESPACE, "applicableInstitution");
        relationship = m.createProperty(NAMESPACE, "relationship");
        relation = m.createProperty(NAMESPACE, "relation");
        name00MatchKey = m.createProperty(NAMESPACE, "name00MatchKey");
        name10MatchKey = m.createProperty(NAMESPACE, "name10MatchKey");
        name11MatchKey = m.createProperty(NAMESPACE, "name11MatchKey");
        name00MarcKey = m.createProperty(NAMESPACE, "name00MarcKey");
        name10MarcKey = m.createProperty(NAMESPACE, "name10MarcKey");
        name11MarcKey = m.createProperty(NAMESPACE, "name11MarcKey");
        primaryContributorName00MatchKey = m.createProperty(NAMESPACE, "primaryContributorName00MatchKey");
        primaryContributorName10MatchKey = m.createProperty(NAMESPACE, "primaryContributorName10MatchKey");
        primaryContributorName11MatchKey = m.createProperty(NAMESPACE, "primaryContributorName11MatchKey");
        title00MatchKey = m.createProperty(NAMESPACE, "title00MatchKey");
        title10MatchKey = m.createProperty(NAMESPACE, "title10MatchKey");
        title11MatchKey = m.createProperty(NAMESPACE, "title11MatchKey");
        title30MatchKey = m.createProperty(NAMESPACE, "title30MatchKey");
        title40MatchKey = m.createProperty(NAMESPACE, "title40MatchKey");
        title00MarcKey = m.createProperty(NAMESPACE, "title00MarcKey");
        title10MarcKey = m.createProperty(NAMESPACE, "title10MarcKey");
        title11MarcKey = m.createProperty(NAMESPACE, "title11MarcKey");
        title30MarcKey = m.createProperty(NAMESPACE, "title30MarcKey");
        title40MarcKey = m.createProperty(NAMESPACE, "title40MarcKey");
        titleSortKey = m.createProperty(NAMESPACE, "titleSortKey");

        locator = m.createProperty(NAMESPACE, "locator");
    }

}
