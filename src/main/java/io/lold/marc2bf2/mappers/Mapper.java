package io.lold.marc2bf2.mappers;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

import java.util.List;
import java.util.Map;

public abstract class Mapper {
    protected Model model;

    /**
     * Create a mapper
     *
     * @param model The Jena Model
     */
    public Mapper(Model model) {
        this.model = model;
    }

    /**
     * Maps value with 00=c00 in field 007 to a list of RDFNode
     *
     * @param value the value of the current position
     * @param prefix the prefix
     * @param mapping The mapping of the value at the current position
     * @return
     * @throws Exception
     */
    public abstract List<RDFNode> map(String value, String prefix, Map<String, Object> mapping) throws Exception;
}
