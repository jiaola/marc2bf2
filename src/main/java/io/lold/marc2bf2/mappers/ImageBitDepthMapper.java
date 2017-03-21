package io.lold.marc2bf2.mappers;

public class ImageBitDepthMapper implements Field007Mapper {
    @Override
    public String mapToLabel(String code) {
        switch (code) {
            case "mmm":
                return "multiple";
            case "nnn":
            case "---":
            case "|||":
                return null;
            default:
                return code;
        }
    }

    @Override
    public String mapToUri(String code) {
        return "ImageBitDepth";
    }
}
