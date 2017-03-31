package io.lold.marc2bf2.mappings;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MappingsReader {
    private static Map<String, Map> mappings;
    static {
        mappings = new HashMap<>();
    }

    public static Map readMappings(String field) throws IOException {
        if (!mappings.containsKey(field)) {
            Yaml yaml = new Yaml();
            ClassLoader classLoader = MappingsReader.class.getClassLoader();
            String file = "mappings/" + field + ".yml";
            mappings.put(field, (Map) yaml.load(classLoader.getResourceAsStream(file)));
        }
        return mappings.get(field);
    }
}
