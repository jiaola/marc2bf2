package io.lold.marc2bf2.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormatUtils {
    final static Logger logger = LoggerFactory.getLogger(FormatUtils.class);

    public static String formatEDTF(String date) {
        if (StringUtils.substring(date, 0, 12).contains("-")) {
            date = date.replaceAll("-", "X");
        }
        String ymd = StringUtils.substring(date, 0, 4) + "-" + StringUtils.substring(date, 4, 6);
        if (!StringUtils.isBlank(StringUtils.substring(date, 6, 8))) {
            ymd += "-" + StringUtils.substring(date, 6, 8);
        }
        if (!StringUtils.isBlank(StringUtils.substring(date, 8, 12))) {
            ymd += "T" + StringUtils.substring(date, 8, 10) + ":" + StringUtils.substring(date, 10, 12) + ":00";
        }
        if (!StringUtils.isBlank(StringUtils.substring(date, 12, 17))) {
            ymd += StringUtils.substring(date, 12, 15) + ":" + date.substring(15, 17);
        }
        return ymd;
    }

    /**
     * Format a 8 digit string to yyyy-mm-dd
     * @param data
     * @return
     */
    public static String formatDate8d(String data) {
        return StringUtils.substring(data, 0, 4) +
                "-" + StringUtils.substring(data, 4, 6) +
                "-" + StringUtils.substring(data, 6, 8);

    }

    public static String formatDate6d(String data) {
        String year = data.substring(0, 2);
        year = Integer.valueOf(year) < 50 ? "20" + year : "19" + year;
        String month = data.substring(2, 4);
        String day = data.substring(4, 6);
        return year + "-" + month + "-" + day;
    }

    public static String chopPunctuation(String str) {
        return chopPunctuation(str, "[.;,:/\\s]+$");
    }

    public static String chopPunctuation(String str, String patterns) {
        return str.replaceAll(patterns, "");
    }

    public static String chopParens(String str) {
        return str.replaceAll("^\\(+|\\)+$", "");
    }

    public static String chopBrackets(String str) {
        return str.replaceAll("^\\[+|]+$", "");
    }
    public static String chopAngularBrackets(String str) {
        return str.replaceAll("^<+|>+$", "");
    }


    /**
     * Validates LCC call number
     *
     * @param str
     * @return
     */
    public static boolean isValidLCC(String str) {
        return str.matches("^[A-Z]{1,3}\\d+.*");
    }
}
