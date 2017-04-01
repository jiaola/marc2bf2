package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.mappings.MappingsReader;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.VocabularyReader;
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

public class DefaultMapper extends Mapper {
    final static Logger logger = LoggerFactory.getLogger(DefaultMapper.class);

    public DefaultMapper(Model model) {
        super(model);
    }

    @Override
    public List<RDFNode> map(String value, Map<String, Object> mapping) throws Exception {
        List<RDFNode> list = new ArrayList<>();

        String label = null, uri = null;

        // if there's code but no label, use the code to get the label
        if (mapping.containsKey("codes")) {  // Use vocabulary to pull data
            String vocName = (String) mapping.get("vocabulary");
            Map<String, String> codeMapping = (Map<String, String>) mapping.get("codes");
            String code = codeMapping.get(value);
            if (code != null) {
                int index = code.indexOf(':');
                if (index > 0) {
                    vocName = code.substring(0, index);
                    code = uri.substring(index + 1);
                }
                Map vocabulary = VocabularyReader.getVocabulary(vocName);
                Map<String, String> vocMap = (Map<String, String>) vocabulary.get(code);
                if (vocMap == null) {
                    logger.warn("No vocabulary mapping found in " + vocName + ", for code " + code);
                    return list;
                }
                uri = vocMap.get("uri");
                label = vocMap.get("label");
            }
        } else if (mapping.containsKey("uris")) { // use uris to build uri
            String prefix = (String) mapping.get("prefix");
            Map<String, String> uris = (Map<String, String>) mapping.get("uris");
            uri = uris == null? null : uris.get(value);
            if (uri != null) {
                int index = uri.indexOf(':');
                if (index > 0) {
                    prefix = uri.substring(0, index);
                    uri = uri.substring(index + 1);
                }
                if (prefix == null) {
                    logger.warn("Prefix is null. This will create invalid URIs");
                }
                uri = mapPrefix(prefix) + uri;
            }
        }
        if (mapping.containsKey("labels")) {
            Map<String, String> labels = (Map<String, String>) mapping.get("labels");
            label = labels.getOrDefault(value, label);
        }

        String type = getType(mapping);

        if (label == null && uri == null) {
            return list;
        }
        RDFNode object = getResource(label, uri, type);
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

    protected RDFNode getResource(String label, String uri, String type) throws Exception {
        Resource object;
        if (uri != null) {
            object = model.createResource(uri);
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

    protected String mapPrefix(String prefix) throws Exception {
        Map prefixMap = MappingsReader.readMappings("prefixes");
        return (String) prefixMap.get(prefix);
    }
}
