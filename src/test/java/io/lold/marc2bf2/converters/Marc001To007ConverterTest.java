package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(Parameterized.class)
public class Marc001To007ConverterTest {
    private Marc001To007Converter converter;
    private Model model;

    @Parameterized.Parameter
    public Record record;

    @Parameterized.Parameters
    public static Record[] records() {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("ConvSpec-001-007/marc.xml");
        MarcXmlReader reader = new MarcXmlReader(input);
        ArrayList<Record> records = new ArrayList<>();
        while (reader.hasNext()) {
            Record record = reader.next();
            records.add(record);
        }
        return records.toArray(new Record[records.size()]);
    }

    @Before
    public void init() {
        model = io.lold.marc2bf2.ModelFactory.createBfModel();
        converter = new Marc001To007Converter(model);
    }

    @Test
    public void testConvert001() {
        System.out.println("001 should set the AdminMetadata identifiedBy property for the work");
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("001")) {
                RDFNode resource = converter.convert001(field);
                assertNotNull(resource);
                assertTrue(resource.isResource());
                Statement stmt = resource.asResource().getProperty(RDF.value);
                assertNotNull(stmt);
                String value = stmt.getLiteral().getString();
                assertEquals(field.getData(), value);
                assertEquals(field.getData(), getLiteralString(model, RDF.value));
            } else {
                assertNull(converter.convert001(field));
            }
        }
    }

    @Test
    public void testConvert003() {
        System.out.println("003 should set the AdminMetadata source property for the work");
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("003")) {
                RDFNode resource = converter.convert003(field);
                assertNotNull(resource);
                assertTrue(resource.isResource());
                Statement stmt = resource.asResource().getProperty(BIB_FRAME.code);
                assertNotNull(stmt);
                assertEquals(field.getData(), stmt.getLiteral().getString());
                assertEquals(field.getData(), getLiteralString(model, BIB_FRAME.code));
            } else {
                assertNull(converter.convert003(field));
            }
        }
    }

    public String getLiteralString(Model model, Property property) {
        NodeIterator iter = model.listObjectsOfProperty(property);
        while (iter.hasNext()) {
            RDFNode node = iter.next();
            if (node.isLiteral()) {
                return node.asLiteral().getString();
            }
        }
        return null;
    }

    @After
    public void tearDown() {
        converter = null;
    }
}
