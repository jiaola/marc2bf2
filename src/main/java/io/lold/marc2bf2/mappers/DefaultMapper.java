package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.mappings.MappingsReader;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultMapper extends Mapper {
    final static Logger logger = LoggerFactory.getLogger(DefaultMapper.class);

    public DefaultMapper(Model model) {
        super(model);
    }

    @Override
    public List<RDFNode> map(String value, String prefix, Map<String, Object> mapping) throws Exception {
        Map<String, String> labels = (Map<String, String>) mapping.get("labels");
        Map<String, String> uris = (Map<String, String>) mapping.get("uris");
        String label = labels == null? null : labels.get(value);
        String uri = uris == null? null : uris.get(value);

        String type = getType(mapping);

        List<RDFNode> list = new ArrayList<>();
        if (label == null && uri == null) {
            return list;
        }
        RDFNode object = getResource(prefix, label, uri, type);
        list.add(object);
        return list;
    }

    /**
     * Returns the label proeprty. Override it if the property is different
     *
     * @return
     */
    protected Property getLabelProeprty() {
        return RDFS.label;
    }

    protected String getType(Map<String, Object> mapping) {
        return (String) mapping.get("type");
    }

    protected RDFNode getResource(String prefix, String label, String uri, String type) {
        Resource object;
        if (uri != null) {
            if (prefix == null) {
                logger.warn("Prefix is null. This will create invalid URIs");
            }
            object = model.createResource(prefix + uri);
        } else {
            object = model.createResource();
        }
        if (type == null) {
            return model.createLiteral(label);
        } else {
            object.addProperty(RDF.type, model.createResource(BIB_FRAME.NAMESPACE + type));
            if (label != null) {
                object.addProperty(getLabelProeprty(), label);
            }
            return object;
        }
    }
}
