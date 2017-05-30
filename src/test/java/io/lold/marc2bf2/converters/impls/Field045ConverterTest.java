package io.lold.marc2bf2.converters.impls;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Field045ConverterTest {
    @Test
    public void testFormat045a() throws Exception {
        assertEquals("-02XX/096X", new Field045Converter(null, null).format045a("d7n6"));
    }
}
