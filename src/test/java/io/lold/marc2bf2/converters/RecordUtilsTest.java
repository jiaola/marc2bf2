package io.lold.marc2bf2.converters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RecordUtilsTest {

    @Parameterized.Parameter
    public Record record;

    @Parameterized.Parameters
    public static Record[] records() {
        return TestUtils.readTestRecords("ConvSpec-880/marc-lang.xml");
    }

    @Test
    public void testGetXmlLang() throws Exception {
        DataField field = (DataField) record.getVariableField("880");
        String xmllang = RecordUtils.getXmlLang(field, record);
        assertEquals("da-arab", xmllang);
    }

    @Test
    public void testFormatEDTF() throws Exception {
        String date = "197809102000-0400";
        assertEquals("1978-09-10T20:00:00-04:00", RecordUtils.formatEDTF(date));
    }
}
