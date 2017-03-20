package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class Field007ConverterTest {
    private Field007Converter converter;
    private Model model;

    @Parameterized.Parameter
    public Record record;

    @Parameterized.Parameters
    public static Record[] records() {
        return TestUtils.readTestRecords("ConvSpec-001-007/marc.xml");
    }

    @Before
    public void setUp() throws Exception {
        model = io.lold.marc2bf2.ModelFactory.createBfModel();
        // create a mock work and adminmetadata
        model.createResource(ModelUtils.getWorkUri(record))
                .addProperty(RDF.type, BIB_FRAME.Work)
                .addProperty(BIB_FRAME.adminMetadata, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.AdminMetadata));
        converter = new Field007Converter(model, record);
    }

    @After
    public void tearDown() throws Exception {
        model.close();
        model = null;
        converter = null;
    }

    @Test
    public void testConvert() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("007")) {
                model = converter.convert(field);
                Resource work = model.getResource(ModelUtils.getWorkUri(record));

                String data = field.getData();
                String c00 = data.substring(0, 1);
                char type = record.getLeader().getTypeOfRecord();
                switch (c00) {
                    case "a":
                    case "d":
                        if (type != 'e' && type != 'f')
                            assertTrue(TestUtils.checkWorkType(work, BIB_FRAME.Cartography));
                        break;
                    case "g":
                    case "k":
                        if (type != 'k')
                            assertTrue(TestUtils.checkWorkType(work, BIB_FRAME.StillImage));
                        break;
                    case "m":
                        if (type != 'g')
                            assertTrue(TestUtils.checkWorkType(work, BIB_FRAME.MovingImage));
                        break;
                    case "s":
                        if (type != 'i' && type != 'j')
                            assertTrue(TestUtils.checkWorkType(work, BIB_FRAME.Audio));
                        break;
                    default:
                        break;
                }
                model.write(System.out);
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }
}
