package io.lold.marc2bf2.utils;

import io.lold.marc2bf2.mappings.MappingsReader;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.*;

import java.util.List;

public class RecordUtils {
    public enum Material {
        Book, ComputerFile, Map, Music, ContinuingResource, VisualMaterial, MixedMaterial
    }

    public static Material getMaterialTypeFromLeader(Record record) {
        Leader leader = record.getLeader();
        char type = leader.getTypeOfRecord();
        char[] impl = leader.getImplDefined1();

        if ((type == 'a' || type == 't') && (impl[0] == 'a' || impl[0] == 'c' || impl[0] == 'd' || impl[0] == 'm')) {
            return Material.Book;
        } else if (type == 'm') {
            return Material.ComputerFile;
        } else if (type == 'e' || type == 'f') {
            return Material.Map;
        } else if (type == 'c' || type == 'd' || type == 'i' || type == 'j') {
            return Material.Music;
        } else if ((type == 'a') && (impl[0] == 'b' || impl[0] == 'i' || impl[0] == 's')) {
            return Material.ContinuingResource;
        } else if (type == 'g' || type == 'k' || type == 'o' || type == 'r') {
            return Material.VisualMaterial;
        } else if (type == 'p') {
            return Material.MixedMaterial;
        }
        return null;
    }

    public static boolean isBookByLeader(Record record) {
        return Material.Book.equals(getMaterialTypeFromLeader(record));
    }

    public static boolean isComputerFileByLeader(Record record) {
        return Material.ComputerFile.equals(getMaterialTypeFromLeader(record));
    }

    public static boolean isMapByLeader(Record record) {
        return Material.Map.equals(getMaterialTypeFromLeader(record));
    }

    public static boolean isMusicByLeader(Record record) {
        return Material.Music.equals(getMaterialTypeFromLeader(record));
    }

    public static boolean isContinuingResourceByLeader(Record record) {
        return Material.ContinuingResource.equals(getMaterialTypeFromLeader(record));
    }

    public static boolean isVisualMaterialByLeader(Record record) {
        return Material.VisualMaterial.equals(getMaterialTypeFromLeader(record));
    }

    public static boolean isMixedMaterialByLeader(Record record) {
        return Material.MixedMaterial.equals(getMaterialTypeFromLeader(record));
    }

    public static Material getMaterialTypeFrom006(Record record) {
        ControlField field = (ControlField) record.getVariableField("006");
        String data = field.getData();
        char c = data.charAt(0);
        switch (c) {
            case 'a':
            case 't':
                return Material.Book;
            case 'm':
                return Material.ComputerFile;
            case 'e':
            case 'f':
                return Material.Map;
            case 'c':
            case 'd':
            case 'i':
            case 'j':
                return Material.Music;
            case 's':
                return Material.ContinuingResource;
            case 'g':
            case 'k':
            case 'o':
            case 'r':
                return Material.VisualMaterial;
            case 'p':
                return Material.MixedMaterial;
            default:
                return null;
        }
    }

    public static boolean isBookBy006(Record record) {
        return Material.Book.equals(getMaterialTypeFrom006(record));
    }

    public static boolean isComputerFileBy006(Record record) {
        return Material.ComputerFile.equals(getMaterialTypeFrom006(record));
    }

    public static boolean isMapBy006(Record record) {
        return Material.Map.equals(getMaterialTypeFrom006(record));
    }

    public static boolean isMusicBy006(Record record) {
        return Material.Music.equals(getMaterialTypeFrom006(record));
    }

    public static boolean isContinuingResourceBy006(Record record) {
        return Material.ContinuingResource.equals(getMaterialTypeFrom006(record));
    }

    public static boolean isVisualMaterialBy006(Record record) {
        return Material.VisualMaterial.equals(getMaterialTypeFrom006(record));
    }

    public static boolean isMixedMaterialBy006(Record record) {
        return Material.MixedMaterial.equals(getMaterialTypeFrom006(record));
    }

    public static String getXmlLang(DataField field, Record record) {
        Subfield sf = field.getSubfield('6');
        if (sf == null) {
            return null;
        }
        ControlField f008 = (ControlField) record.getVariableField("008");
        String lang008 = f008.getData().substring(35, 38);
        String lang = MappingsReader.getLanguageMapping().get(lang008);
        String[] vscript = sf.getData().split("/");
        if (vscript.length < 1) return null;

        if (vscript[1].equals("(3")) return lang + "-arab";
        else if (vscript[1].equals("(B")) return lang + "-latn";
        else if (vscript[1].equals("$1") && lang008.equals("kor")) return lang + "-hang";
        else if (vscript[1].equals("$1") && lang008.equals("chi")) return lang + "-hani";
        else if (vscript[1].equals("$1") && lang008.equals("jpn")) return lang + "-jpan";
        else if (vscript[1].equals("(N")) return lang + "-cyrl";
        else if (vscript[1].equals("(S")) return lang + "-grek";
        else if (vscript[1].equals("(2")) return lang + "-hebr";
        else return null;
    }

    /**
     * From the current index, look ahead for n more subfields
     * to find a subfield with certain code
     * @param field
     * @param i
     * @param code
     * @return
     */
    public static Subfield lookAhead(DataField field, int i, int n, char code) {
        List<Subfield> subfieldList = field.getSubfields();
        int j = 1;
        while (j <= n && i+j < subfieldList.size()) {
            Subfield sf = subfieldList.get(i+j);
            if (sf.getCode() == code) return sf;
            j++;
        }
        return null;
    }

}
