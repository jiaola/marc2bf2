package io.lold.marc2bf2.mappings;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Map;

public class MappingsReaderTest {
    @Test
    public void testReadMappings() throws Exception{
        Map map = MappingsReader.readMappings("007");
        assertEquals("Cartography", ((Map)((Map)map.get("Work")).get("a")).get("type"));
    }

    @Test
    public void testGetLanguageMapping() throws Exception {
        Map<String, String> map = MappingsReader.getLanguageMapping();
        assertEquals("af", map.get("afr"));
        assertEquals("sq", map.get("sqi"));
    }
}
