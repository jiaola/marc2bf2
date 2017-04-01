package io.lold.marc2bf2.converters;

import org.marc4j.marc.Leader;
import org.marc4j.marc.Record;

public class RecordUtils {
    public enum Material {
        Book, ComputerFile, Map, Music, ContinuingResource, VisualMaterial, MixedMaterial
    }

    public static Material getMaterial(Record record) {
        if (isBook(record)) {
            return Material.Book;
        } else if (isComputerFile(record)) {
            return Material.ComputerFile;
        } else if (isMap(record)) {
            return Material.Map;
        } else if (isMusic(record)) {
            return Material.Music;
        } else if (isContinuingResource(record)) {
            return Material.ContinuingResource;
        } else if (isVisualMaterial(record)) {
            return Material.VisualMaterial;
        } else if (isMixedMaterial(record)) {
            return Material.MixedMaterial;
        }
        return null;
    }

    public static boolean isBook(Record record) {
        Leader leader = record.getLeader();
        char type = leader.getTypeOfRecord();
        char[] impl = leader.getImplDefined1();
        return (type == 'a' || type == 't') && (impl[0] == 'a' || impl[0] == 'c' || impl[0] == 'd' || impl[0] == 'm');
    }

    public static boolean isComputerFile(Record record) {
        Leader leader = record.getLeader();
        char type = leader.getTypeOfRecord();
        return type == 'm';
    }

    public static boolean isMap(Record record) {
        Leader leader = record.getLeader();
        char type = leader.getTypeOfRecord();
        return type == 'e' || type == 'f';
    }

    public static boolean isMusic(Record record) {
        Leader leader = record.getLeader();
        char type = leader.getTypeOfRecord();
        return type == 'c' || type == 'd' || type == 'i' || type == 'j';
    }

    public static boolean isContinuingResource (Record record) {
        Leader leader = record.getLeader();
        char type = leader.getTypeOfRecord();
        char[] impl = leader.getImplDefined1();
        return (type == 'a') && (impl[0] == 'b' || impl[0] == 'i' || impl[0] == 's');
    }

    public static boolean isVisualMaterial(Record record) {
        Leader leader = record.getLeader();
        char type = leader.getTypeOfRecord();
        return type == 'g' || type == 'k' || type == 'o' || type == 'r';
    }

    public static boolean isMixedMaterial(Record record) {
        Leader leader = record.getLeader();
        char type = leader.getTypeOfRecord();
        return type == 'p';
    }
}
