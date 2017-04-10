package io.lold.marc2bf2.utils;

import io.lold.marc2bf2.MarcDataException;
import io.lold.marc2bf2.mappings.MappingsReader;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.MarcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import static java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FormatUtils {
    final static Logger logger = LoggerFactory.getLogger(FormatUtils.class);

    private static Map<String, String> marcTimeMap = null;

    public static String formatEDTF(String date) {
        if (date.substring(0, 12).contains("-")) {
            date = date.replaceFirst("-", "X");
        }
        String ymd = date.substring(0, 4) + "-" + date.substring(4, 6);
        if (!StringUtils.isBlank(date.substring(6, 8))) {
            ymd += "-" + date.substring(6, 8);
        }
        if (!StringUtils.isBlank(date.substring(8, 12))) {
            ymd += "T" + date.substring(8, 10) + ":" + date.substring(10, 12) + ":00";
        }
        if (!StringUtils.isBlank(date.substring(12, 17))) {
            ymd += date.substring(12, 15) + ":" + date.substring(15, 17);
        }
        return ymd;
    }

    public static String chopPunctuation(String str) {
        return chopPunctuation(str, "[.;:/\\s]+$");
    }

    public static String chopPunctuation(String str, String patterns) {
        return str.replaceAll(patterns, "");
    }

    public static String format045a(String data) throws MarcDataException {
        if (marcTimeMap == null) {
            marcTimeMap = Collections.unmodifiableMap(Stream.of(
                    new SimpleEntry<>("a0", "-XXXX/-3000"),
                    new SimpleEntry<>("b0", "-29XX"),
                    new SimpleEntry<>("b1", "-28XX"),
                    new SimpleEntry<>("b2", "-27XX"),
                    new SimpleEntry<>("b3", "-26XX"),
                    new SimpleEntry<>("b4", "-25XX"),
                    new SimpleEntry<>("b5", "-24XX"),
                    new SimpleEntry<>("b6", "-23XX"),
                    new SimpleEntry<>("b7", "-22XX"),
                    new SimpleEntry<>("b8", "-21XX"),
                    new SimpleEntry<>("b9", "-20XX"),
                    new SimpleEntry<>("c0", "-19XX"),
                    new SimpleEntry<>("c1", "-18XX"),
                    new SimpleEntry<>("c2", "-17XX"),
                    new SimpleEntry<>("c3", "-16XX"),
                    new SimpleEntry<>("c4", "-15XX"),
                    new SimpleEntry<>("c5", "-14XX"),
                    new SimpleEntry<>("c6", "-13XX"),
                    new SimpleEntry<>("c7", "-12XX"),
                    new SimpleEntry<>("c8", "-11XX"),
                    new SimpleEntry<>("c9", "-10XX"),
                    new SimpleEntry<>("d0", "-09XX"),
                    new SimpleEntry<>("d1", "-08XX"),
                    new SimpleEntry<>("d2", "-07XX"),
                    new SimpleEntry<>("d3", "-06XX"),
                    new SimpleEntry<>("d4", "-05XX"),
                    new SimpleEntry<>("d5", "-04XX"),
                    new SimpleEntry<>("d6", "-03XX"),
                    new SimpleEntry<>("d7", "-02XX"),
                    new SimpleEntry<>("d8", "-01XX"),
                    new SimpleEntry<>("d9", "-00XX"),
                    new SimpleEntry<>("e", "00"),
                    new SimpleEntry<>("f", "01"),
                    new SimpleEntry<>("g", "02"),
                    new SimpleEntry<>("h", "03"),
                    new SimpleEntry<>("i", "04"),
                    new SimpleEntry<>("j", "05"),
                    new SimpleEntry<>("k", "06"),
                    new SimpleEntry<>("l", "07"),
                    new SimpleEntry<>("m", "08"),
                    new SimpleEntry<>("n", "09"),
                    new SimpleEntry<>("o", "10"),
                    new SimpleEntry<>("p", "11"),
                    new SimpleEntry<>("q", "12"),
                    new SimpleEntry<>("r", "13"),
                    new SimpleEntry<>("s", "14"),
                    new SimpleEntry<>("t", "15"),
                    new SimpleEntry<>("u", "16"),
                    new SimpleEntry<>("v", "17"),
                    new SimpleEntry<>("w", "18"),
                    new SimpleEntry<>("x", "19"),
                    new SimpleEntry<>("y", "20"))
                    .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));
        }
        if (data.length() < 4) {
            throw new MarcDataException(data + " is too short. Need to be longer than 4.");
        }
        String date1;
        if ("abced".contains(data.substring(0, 1))) {
            date1 = marcTimeMap.get(data.substring(0, 2));
        } else {
            date1 = marcTimeMap.get(data.substring(0, 1)) + data.substring(1, 2) + "X";
        }
        String date2;
        if ("abced".contains(data.substring(2, 3))) {
            date2 = marcTimeMap.get(data.substring(2, 4));
        } else {
            date2 = marcTimeMap.get(data.substring(2, 3)) + data.substring(3, 4) + "X";
        }
        if (date1.equals(date2)) {
            return date1;
        } else {
            return date1 + "/" + date2;
        }
    }
}
