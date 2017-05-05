package io.lold.marc2bf2.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class FormatUtilsTest {
    @Test
    public void testFormat045a() throws Exception {
        assertEquals("-02XX/096X", FormatUtils.format045a("d7n6"));
    }

    @Test
    public void testChopPunctuation() throws Exception {
        String str = "ill. ;";
        String newStr = FormatUtils.chopPunctuation(str);
        assertEquals("ill", newStr);
    }
}
