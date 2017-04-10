package io.lold.marc2bf2.utils;

import org.apache.commons.lang3.StringUtils;

public class FormatUtils {
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
}
