package io.lold.marc2bf2;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.junit.Assert;
import org.junit.Test;

public class ModelFactoryTest {

    @Test
    public void testCreateBfModel() {
        Model model = ModelFactory.createBfModel();
        Assert.assertEquals(BIB_FRAME.getURI(), model.getNsPrefixURI(BIB_FRAME.PREFIX));
        model.close();
    }
}
