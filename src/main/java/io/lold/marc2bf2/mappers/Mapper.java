package io.lold.marc2bf2.mappers;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

import java.util.List;
import java.util.Map;

public abstract class Mapper {
    protected Map<String, Object> mapping;
    protected Model model;

    /**
     * Create a mapper
     *
     * @param mapping The mapping of the value at the current position
     * @param model The Jena Model
     */
    public Mapper(Map<String, Object> mapping, Model model) {
        this.mapping = mapping;
        this.model = model;
    }

    /**
     * Maps value with 00=c00 in field 007 to a list of RDFNode
     *
     * @param c00 category value (first char in 007)
     * @param value the value of the current position
     * @param config local config
     * @return
     * @throws Exception
     */
    public abstract List<RDFNode> map(String c00, String value, Map<String, Object> config) throws Exception;
}