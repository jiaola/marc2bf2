package io.lold.marc2bf2.mappers;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ImageBitDepthMapper extends DefaultMapper {
    /**
     * Create a mapper
     *
     * @param mapping The mapping of the value at the current position
     * @param model   The Jena Model
     */
    public ImageBitDepthMapper(Map<String, Object> mapping, Model model) {
        super(mapping, model);
    }

    private String mapToLabel(String value) {
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
    public List<RDFNode> map(String c00, String value, Map<String, Object> config) throws Exception {
        String prefix = getPrefix(config);

        List<RDFNode> list = new ArrayList<>();
        String label = mapToLabel(value);
        if (label == null) {
            return list;
        }
        RDFNode object = getResource(prefix, label, "ImageBitDepth");
        list.add(object);
        return list;
    }
}
