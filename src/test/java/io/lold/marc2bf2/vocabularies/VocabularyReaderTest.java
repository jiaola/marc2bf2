package io.lold.marc2bf2.vocabularies;

import io.lold.marc2bf2.mappings.MappingsReader;
import io.lold.marc2bf2.vocabulary.VocabularyReader;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VocabularyReaderTest {
    @Test
    public void testGetVocabulary() throws Exception{
        Map vocabulary = VocabularyReader.getVocabulary("marcgt");
        Map<String, String> map = (Map<String, String>) vocabulary.get("abs");
        assertEquals("http://id.loc.gov/vocabulary/marcgt/abs", map.get("uri"));
    }
}
