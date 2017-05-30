package io.lold.marc2bf2.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class FormatUtilsTest {

    @Test
    public void testChopPunctuation() throws Exception {
        String str = "ill. ;";
        String newStr = FormatUtils.chopPunctuation(str);
        assertEquals("ill", newStr);
    }
}
