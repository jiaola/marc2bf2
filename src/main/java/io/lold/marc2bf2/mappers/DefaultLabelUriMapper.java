package io.lold.marc2bf2.mappers;

import java.util.Map;

public class DefaultLabelUriMapper implements Field007Mapper {
    Map<String, String> labelMap;
    Map<String, String> uriMap;

    public DefaultLabelUriMapper(Map<String, String> labelMap, Map<String, String> uriMap) {
        this.labelMap = labelMap;
        this.uriMap = uriMap;
    }

    @Override
    public String mapToLabel(String code) {
        return labelMap == null ? null : labelMap.get(code);
    }

    @Override
    public String mapToUri(String code) {
        return uriMap == null ? null : uriMap.get(code);
    }
}
