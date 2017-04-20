package io.lold.marc2bf2.utils;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.Record;

public class ModelUtils {
    /**
     * Get the work node from a model
     * @param model
     * @param record
     * @return
     */
    public static Resource getWork(Model model, Record record) {
        return model.getResource(buildUri(record, "Work"));
    }

    /**
     * Get the Instance node from a model
     *
     * @param model
     * @param record
     * @return
     */
    public static Resource getInstance(Model model, Record record) {
        return model.getResource(buildUri(record, "Instance"));
    }

    /**
     * Get the AdminMetadata node from a model
     * @param model
     * @param record
     * @return
     */
    public static Resource getAdminMatadata(Model model, Record record) {
        Resource work = getWork(model, record);
        return work.getPropertyResourceValue(BIB_FRAME.adminMetadata);
    }

    /**
     * Returns a work or instance URI for a record
     * TODO: Override this
     *
     * @param record
     * @return
     */
    public static String buildUri(Record record, String type) {
        //TODO: Set the prefix in a config file
        String prefix = "http://example.org/";
        return prefix + record.getControlNumber() + "#" + type;
    }

    /**
     * Returns an URI for a record that requires an index. For example, it can build
     * URI for items.
     * TODO: Override this
     *
     * @param record
     * @return
     */
    public static String buildUri(Record record, String type, int index) {
        //TODO: Set the prefix in a config file
        String prefix = "http://example.org/";
        return prefix + record.getControlNumber() + "#" + type + "-" + index;
    }

    public static Resource createNote(Model model, String label) {
        return model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Note)
                .addProperty(RDFS.label, label);
    }

    public static Resource createSource(Model model, String label) {
        return model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Source)
                .addProperty(RDFS.label, label);
    }

    public static Resource createLabeledResource(Model model, String label, Resource type) {
        return model.createResource()
                .addProperty(RDF.type, type)
                .addProperty(RDFS.label, label);
    }

    /**
     * Get a full URI given the abbreviated form. For example,
     * given bflc:encodingLevel, it returns
     * http://id.loc.gov/ontologies/bflc/encodingLevel
     *
     * @param value
     * @param model
     * @return
     */
    public static String buildUri(String value, Model model) {
        String[] values = value.trim().split(":");
        if (values.length == 1) {
            return BIB_FRAME.NAMESPACE + values[0].trim();
        } else {
            String nsUri = model.getNsPrefixURI(values[0].trim());
            return nsUri + values[1].trim();
        }
    }

    public static Resource getResource(String type, Model model) {
        String[] values = type.trim().split(":");
        if (values.length == 1) {
            return model.createResource(BIB_FRAME.getURI() + values[0].trim());
        } else {
            String nsUri = model.getNsPrefixURI(values[0].trim());
            return model.createResource(nsUri + values[1].trim());
        }
    }

    public static Property getProperty(String property, Model model) {
        String[] values = property.trim().split(":");
        if (values.length == 1) {
            return model.createProperty(BIB_FRAME.getURI() + values[0].trim());
        } else {
            String nsUri = model.getNsPrefixURI(values[0].trim());
            return model.createProperty(nsUri + values[1].trim());
        }
    }
}
