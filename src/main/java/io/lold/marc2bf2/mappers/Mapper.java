package io.lold.marc2bf2.mappers;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

import java.lang.reflect.Constructor;
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
     * @param mapping The mapping of the value at the current position
     * @return
     * @throws Exception
     */
    public abstract List<RDFNode> map(String value, Map<String, Object> mapping) throws Exception;

    public static Mapper createMapper(String className, Model model) throws Exception {
        if (className == null) {
            return new DefaultMapper(model);
        } else {
            Class<?> clazz = Class.forName(className);
            Constructor<?> cons = clazz.getConstructor(Model.class);
            return (Mapper) cons.newInstance(model);
        }
    }
}
