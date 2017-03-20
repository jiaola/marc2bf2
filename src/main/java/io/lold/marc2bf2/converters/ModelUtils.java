package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.Record;

public class ModelUtils {
    /**
     * Get the work node from a model
     * @param model
     * @param record
     * @return
     */
    public static Resource getWork(Model model, Record record) {
        return model.getResource(getUri(record, "Work"));
    }

    /**
     * Get the Instance node from a model
     *
     * @param model
     * @param record
     * @return
     */
    public static Resource getInstance(Model model, Record record) {
        return model.getResource(getUri(record, "Instance"));
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
     * Returns a URI for a work given a record
     *
     * @param record
     * @return
     */
    public static String getUri(Record record, String type) {
        //TODO: Set the prefix in a config file
        String prefix = "http://example.org/";
        return prefix + record.getControlNumber() + "#" + type;
    }
}
