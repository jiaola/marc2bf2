package io.lold.marc2bf2.mappings;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

public class MappingsReader {
    public static Map readMappings(String file) throws IOException {
        Yaml yaml = new Yaml();
        ClassLoader classLoader = MappingsReader.class.getClassLoader();
        return (Map)yaml.load(classLoader.getResourceAsStream("mappings/" + file));
    }
}
