package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReductionRatioMapper extends DefaultMapper {
    public ReductionRatioMapper(Model model) {
        super(model);
    }

    private String mapToLabel(String value) {
        switch (value) {
            case "---":
            case "|||":
                return null;
            default:
                return value;
        }
    }

    @Override
    public List<RDFNode> map(String value, String prefix, Map<String, Object> mapping) throws Exception {
        String first = value.substring(0, 1);

        Map<String, Object> labels = (Map<String, Object>) mapping.get("labels");
        String noteLabel = labels == null? null : (String) labels.get(first);
        String label = mapToLabel(value.substring(1));
        List<RDFNode> list = new ArrayList<>();
        if (noteLabel == null && label == null) {
            return list;
        }

        Resource object = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.ReductionRatio);
        if (noteLabel != null) {
            Resource note = model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Note)
                    .addProperty(RDFS.label, noteLabel);
            object.addProperty(BIB_FRAME.note, note);
        }
        if (label != null) {
            object.addProperty(RDFS.label, label);
        }
        list.add(object);
        return list;
    }
}
