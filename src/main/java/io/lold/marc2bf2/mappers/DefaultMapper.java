package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.mappings.MappingsReader;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
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

    public DefaultMapper(Map<String, Object> mapping, Model model) {
        super(mapping, model);
    }

    @Override
    public List<RDFNode> map(String c00, String value, Map<String, Object> config) throws Exception {
        String prefix = getPrefix(config);

        Map<String, Object> labels = (Map<String, Object>) mapping.get("labels");
        Map<String, Object> uris = (Map<String, Object>) mapping.get("uris");
        Object label = labels == null? null : labels.get(value);
        Object uri = uris == null? null : uris.get(value);

        List<RDFNode> list = new ArrayList<>();
        if (label == null && uri == null) {
            return list;
        }
        if (label instanceof String) {
            RDFNode object = getResource(prefix, (String)label, (String)uri);
            list.add(object);
        } else { // it's a list
            List<String> labelList = (List<String>) label;
            List<String> uriList = (List<String>) uri;
            if (labelList.size() != uriList.size()) {
                logger.error("Invalid label/uri mapping: " + c00 + ", " + value);
            }
            for (int i = 0; i < labelList.size(); i++) {
                RDFNode object = getResource(prefix, labelList.get(i), uriList.get(i));
                list.add(object);
            }
        }
        return list;
    }

    protected String getPrefix(Map<String, Object> config) throws Exception {
        String ns = Optional.ofNullable(getConfigPrefix(config)).orElse((String) mapping.get("prefix"));
        Map prefixMap = MappingsReader.readMappings("prefixes");
        return (String) prefixMap.get(ns);
    }

    protected String getConfigPrefix(Map config) {
        Map<String, String> prefixes = (Map<String, String>) config.get("prefixes");
        return prefixes == null? null : prefixes.get("cpos");
    }

    protected RDFNode getResource(String prefix, String label, String uri) {
        Resource object;
        if (uri != null) {
            if (prefix == null) {
                logger.warn("Prefix is null. This will create invalid URIs");
            }
            object = model.createResource(prefix + uri);
        } else {
            object = model.createResource();
        }
        String type = (String) mapping.get("type");
        if (type == null) {
            return model.createLiteral(label);
        } else {
            object.addProperty(RDF.type, model.createResource(BIB_FRAME.NAMESPACE + type));
            if (label != null) {
                object.addProperty(RDFS.label, label);
            }
            return object;
        }
    }
}
