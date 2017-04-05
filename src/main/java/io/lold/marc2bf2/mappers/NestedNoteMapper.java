package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NestedNoteMapper extends DefaultMapper {
    public NestedNoteMapper(Model model) {
        super(model);
    }

    @Override
    public List<RDFNode> map(String value, Map<String, Object> mapping) throws Exception {
        List<RDFNode> nodes = new ArrayList<>();
        Map<String, String> labels = (Map<String, String>) mapping.get("labels");
        String label = labels.get(value);
        if (label == null) {
            return nodes;
        }
        Resource note = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Note)
                .addProperty(RDFS.label, label)
                .addProperty(BIB_FRAME.noteType, (String) params.get("noteType"));
        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Cartographic)
                .addProperty(BIB_FRAME.note, note);
        nodes.add(resource);
        return nodes;
    }
}
