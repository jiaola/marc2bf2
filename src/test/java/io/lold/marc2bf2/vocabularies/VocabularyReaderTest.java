package io.lold.marc2bf2.vocabularies;

import io.lold.marc2bf2.vocabulary.VocabularyReader;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VocabularyReaderTest {
    @Test
    public void testGetVocabulary() throws Exception {
        Map vocabulary = VocabularyReader.getVocabulary("marcgt");
        Map<String, String> map = (Map<String, String>) vocabulary.get("abs");
        assertEquals("http://id.loc.gov/vocabulary/marcgt/abs", map.get("uri"));
    }

    @Test
    public void testGetFrequencies() throws Exception {
        Map vocabulary = VocabularyReader.getVocabulary("frequencies");
        Map<String, String> map = (Map<String, String>) vocabulary.get("ann");
        assertEquals("http://id.loc.gov/vocabulary/frequencies/ann", map.get("uri"));
    }
}
