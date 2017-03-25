package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import java.util.List;
import java.util.Map;

public class CompletenessNoteMapper extends DefaultMapper {
    public CompletenessNoteMapper(Model model) {
        super(model);
    }

    @Override
    public List<RDFNode> map(String c00, String value, Map<String, Object> config, Map<String, Object> mapping) throws Exception {
        List<RDFNode> list = super.map(c00, value, config, mapping);
        list.forEach(node -> ((Resource)node).addProperty(BIB_FRAME.noteType, "completeness"));
        return list;
    }
}
