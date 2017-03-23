package io.lold.marc2bf2.mappings;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MappingsReader {
    private static Map<String, Map> mappings;
    private static Map<String, String> mappingFiles;
    static {
        mappings = new HashMap<>();
        mappingFiles = new HashMap<>();
        // Should make this configuraable
        mappingFiles.put("007", "mappings/007.yml");
        mappingFiles.put("prefixes", "mappings/prefixes.yml");
    }

    public static Map readMappings(String field) throws IOException {
        if (mappings.containsKey(field)) {
            return mappings.get(field);
        } else {
            if (!mappingFiles.containsKey(field)) {
                throw new FileNotFoundException("Couldn't find a mapping file for field " + field);
            }
            Yaml yaml = new Yaml();
            ClassLoader classLoader = MappingsReader.class.getClassLoader();
            return (Map) yaml.load(classLoader.getResourceAsStream(mappingFiles.get(field)));
        }
    }
}
