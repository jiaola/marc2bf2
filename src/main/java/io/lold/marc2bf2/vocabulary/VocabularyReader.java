package io.lold.marc2bf2.vocabulary;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDFS;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class VocabularyReader {
    private static Map<String, Map> vocMapping;
    static {
        vocMapping = new HashMap<>();
    }

    public static Map getVocabulary(String type) throws IOException {
        if (!vocMapping.containsKey(type)) {
            Model model = ModelFactory.createDefaultModel();
            ClassLoader classLoader = VocabularyReader.class.getClassLoader();
            String file = "vocabularies/" + type + ".rdf";
            InputStream is = classLoader.getResourceAsStream(file);
            model.read(is, "");

            Map<String, Map<String, String>> vocabulary = new HashMap<>();

            NodeIterator iter = model.listObjectsOfProperty(MADS_RDF.hasTopMemberOfMADSScheme);
            while (iter.hasNext()) {
                Resource resource = (Resource) iter.next();
                Map<String, String> map = new HashMap<>();

                StmtIterator sIter = resource.listProperties(RDFS.label, "en");
                if (sIter.hasNext()) {
                    String label = sIter.nextStatement().getLiteral().getString();
                    map.put("label", label);
                }
                map.put("uri", resource.getURI());
                sIter = resource.listProperties(MADS_RDF.code);
                while (sIter.hasNext()) {
                    String code = sIter.nextStatement().getLiteral().getString();
                    vocabulary.put(code, map);
                }
            }
            vocMapping.put(type, vocabulary);
            model.close();
        }
        return vocMapping.get(type);
    }
}
