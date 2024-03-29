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

public class ImageBitDepthMapper extends DefaultMapper {
    public ImageBitDepthMapper(Model model) {
        super(model);
    }

    protected String mapToLabel(String value) {
        switch (value) {
            case "mmm":
                return "multiple";
            case "nnn":
            case "---":
            case "|||":
                return null;
            default:
                return value;
        }
    }

    @Override
    public List<RDFNode> map(String value, Map<String, Object> mapping) throws Exception {
        List<RDFNode> list = new ArrayList<>();
        String label = mapToLabel(value);
        if (label == null) {
            return list;
        }
        Resource object = model.createResource();
        object.addProperty(RDF.type, model.createResource("http://id.loc.gov/ontologies/bflc/ImageBitDepth"));
        object.addProperty(RDF.type, BIB_FRAME.DigitalCharacteristic);
        object.addProperty(RDFS.label, label);
        list.add(object);
        return list;
    }
}
