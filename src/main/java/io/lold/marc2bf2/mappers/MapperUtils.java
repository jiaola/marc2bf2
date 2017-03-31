package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.mappings.MappingsReader;

import java.util.Map;
import java.util.Optional;

public class MapperUtils {
    public static String getPrefix(Map<String, Object> config, Map<String, Object> mapping) throws Exception {
        String ns = Optional.ofNullable(getConfigPrefix(config)).orElse((String) mapping.get("prefix"));
        Map prefixMap = MappingsReader.readMappings("prefixes");
        return (String) prefixMap.get(ns);
    }

    protected static String getConfigPrefix(Map config) {
        if (config == null)
            return null;
        Map<String, String> prefixes = (Map<String, String>) config.get("prefixes");
        return prefixes == null? null : prefixes.get("cpos");
    }
}
