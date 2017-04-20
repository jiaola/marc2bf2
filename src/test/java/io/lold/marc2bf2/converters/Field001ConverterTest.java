package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.TestUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.RDF;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class Field001ConverterTest {
    private Field001Converter converter;
    private Model model;

    @Parameterized.Parameter
    public Record record;

    @Parameterized.Parameters
    public static Record[] records() {
        return TestUtils.readTestRecords("ConvSpec-001-007/marc.xml");
    }

    @Before
    public void setUp() {
        model = io.lold.marc2bf2.ModelFactory.createBfModel();
        // create a mock work and adminmetadata
        model.createResource(ModelUtils.buildUri(record, "Work"))
                .addProperty(RDF.type, BIB_FRAME.Work)
                .addProperty(BIB_FRAME.adminMetadata, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.AdminMetadata));
        converter = new Field001Converter(model, record);
    }

    @After
    public void tearDown() throws Exception {
        model.close();
        model = null;
        converter = null;
    }

    @Test
    public void testConvert() {
        System.out.println("001 should set the AdminMetadata identifiedBy property for the work");
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("001")) {
                model = converter.convert(field);
                Resource resource = model.getResource(ModelUtils.buildUri(record, "Work"))
                        .getPropertyResourceValue(BIB_FRAME.adminMetadata)
                        .getPropertyResourceValue(BIB_FRAME.identifiedBy);
                Statement stmt = resource.asResource().getProperty(RDF.value);
                assertNotNull(stmt);
                assertEquals(field.getData(), stmt.getLiteral().getString());
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }
}
