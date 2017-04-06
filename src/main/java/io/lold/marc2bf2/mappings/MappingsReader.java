package io.lold.marc2bf2.mappings;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingsReader {
    private static Map<String, Map> mappings;
    private static Map<String, String> languages;
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

    public static Map<String, String> getLanguageMapping() {
        if (languages == null) {
            languages = new HashMap<>();
            Yaml yaml = new Yaml();
            ClassLoader classLoader = MappingsReader.class.getClassLoader();
            String file = "mappings/language_crosswalk.yml";
            Map map = (Map) yaml.load(classLoader.getResourceAsStream(file));
            List<Map> list = (List<Map>) ((Map) map.get("xml-langs")).get("language");
            for (Map lang: list) {
                Object iso6392 = lang.get("iso6392");
                if (iso6392 instanceof String) {
                    languages.put((String) iso6392, (String) lang.get("_xmllang"));
                } else if (iso6392 instanceof Collection<?>) {
                    List<String> iso6392List = (List<String>) iso6392;
                    iso6392List.forEach( i ->
                            languages.put(i, (String) lang.get("_xmllang"))
                    );
                }
            }
        }
        return languages;
    }
}
