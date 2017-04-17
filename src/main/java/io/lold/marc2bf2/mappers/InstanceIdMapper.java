package io.lold.marc2bf2.mappers;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

import java.util.List;
import java.util.Map;

public class InstanceIdMapper extends Mapper {
    public InstanceIdMapper(Model model) {
        super(model);
    }

    @Override
    public List<RDFNode> map(String value, Map<String, Object> mapping) throws Exception {
        return null;
    }
}
