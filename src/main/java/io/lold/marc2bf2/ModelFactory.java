package io.lold.marc2bf2;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.shared.PrefixMapping;

/**
 * Factory for creating BibFrame models
 */
public class ModelFactory {
    public static Model createBfModel() {
        Model model = org.apache.jena.rdf.model.ModelFactory.createDefaultModel();

        // Register the namespace prefixes
        PrefixMapping map = PrefixMapping.Factory.create()
                .setNsPrefix(BIB_FRAME.PREFIX, BIB_FRAME.getURI())
                .setNsPrefix(BIB_FRAME_LC.PREFIX, BIB_FRAME_LC.getURI());
        model.setNsPrefixes(map);
        return model;
    }
}
