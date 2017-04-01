package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import java.util.List;
import java.util.Map;

public class BibFrameCodeMapper extends DefaultMapper {
    public BibFrameCodeMapper(Model model) {
        super(model);
    }

    @Override
    public List<RDFNode> map(String value, Map<String, Object> mapping) throws Exception {
        List<RDFNode> nodes = super.map(value, mapping);
        for (RDFNode node: nodes) {
            Resource resource = node.asResource();
            resource.addProperty(BIB_FRAME.code, value);
        }
        return nodes;
    }
}
