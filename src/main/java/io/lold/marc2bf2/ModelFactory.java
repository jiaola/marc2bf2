package io.lold.marc2bf2;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import io.lold.marc2bf2.vocabulary.MADS_RDF;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

/**
 * Factory for creating BibFrame models
 */
public class ModelFactory {
    private static PrefixMapping PREFIX_MAPPING = null;

    public static PrefixMapping prefixMapping() {
        if (PREFIX_MAPPING == null) {
            PREFIX_MAPPING = PrefixMapping.Factory.create()
                    .setNsPrefix(BIB_FRAME.PREFIX, BIB_FRAME.getURI())
                    .setNsPrefix(BIB_FRAME_LC.PREFIX, BIB_FRAME_LC.getURI())
                    .setNsPrefix("carrier", "http://id.loc.gov/vocabulary/carrier/")
                    .setNsPrefix("carriers", "http://id.loc.gov/vocabulary/carriers/")
                    .setNsPrefix("demographicTerms", "http://id.loc.gov/authorities/demographicTerms/")
                    .setNsPrefix("classSchemes", "http://id.loc.gov/vocabulary/classSchemes/")
                    .setNsPrefix("contentType", "http://id.loc.gov/vocabulary/contentType/")
                    .setNsPrefix("contentTypes", "http://id.loc.gov/vocabulary/contentTypes/")
                    .setNsPrefix("countries", "http://id.loc.gov/vocabulary/countries/")
                    .setNsPrefix("descriptionConventions", "http://id.loc.gov/vocabulary/descriptionConventions/")
                    .setNsPrefix("genreForms", "http://id.loc.gov/authorities/genreForms/")
                    .setNsPrefix("geographicAreas", "http://id.loc.gov/vocabulary/geographicAreas/")
                    .setNsPrefix("graphicMaterials", "http://id.loc.gov/vocabulary/graphicMaterials/")
                    .setNsPrefix("issuance", "http://id.loc.gov/vocabulary/issuance/")
                    .setNsPrefix("languages", "http://id.loc.gov/vocabulary/languages/")
                    .setNsPrefix("marcgt", "http://id.loc.gov/vocabulary/marcgt/")
                    .setNsPrefix("mcolor", "http://id.loc.gov/vocabulary/mcolor/")
                    .setNsPrefix("mediaType", "http://id.loc.gov/vocabulary/mediaType/")
                    .setNsPrefix("mediaTypes", "http://id.loc.gov/vocabulary/mediaTypes/")
                    .setNsPrefix("mmaterial", "http://id.loc.gov/vocabulary/mmaterial/")
                    .setNsPrefix("mplayback", "http://id.loc.gov/vocabulary/mplayback/")
                    .setNsPrefix("mpolarity", "http://id.loc.gov/vocabulary/mpolarity/")
                    .setNsPrefix("marcauthen", "http://id.loc.gov/vocabulary/marcauthen/")
                    .setNsPrefix("marcmuscomp", "http://id.loc.gov/vocabulary/marcmuscomp/")
                    .setNsPrefix("organizations", "http://id.loc.gov/vocabulary/organizations/")
                    .setNsPrefix("madsrdf", "http://www.loc.gov/mads/rdf/v1#")
                    .setNsPrefix("relators", "http://id.loc.gov/vocabulary/relators/");
        }
        return PREFIX_MAPPING;
    }

    public static Model createBfModel() {
        Model model = org.apache.jena.rdf.model.ModelFactory.createDefaultModel();

        // Register the namespace prefixes
        PrefixMapping map = PrefixMapping.Factory.create()
                .setNsPrefix(BIB_FRAME.PREFIX, BIB_FRAME.getURI())
                .setNsPrefix(BIB_FRAME_LC.PREFIX, BIB_FRAME_LC.getURI())
                .setNsPrefix(MADS_RDF.PREFIX, MADS_RDF.getURI())
                .setNsPrefix("rdfs", RDFS.getURI())
                .setNsPrefix("rdf", RDF.getURI());
        model.setNsPrefixes(map);
        return model;
    }
}
