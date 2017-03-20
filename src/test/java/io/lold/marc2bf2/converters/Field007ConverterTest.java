package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
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
                .addProperty(RDF.type, BIB_FRAME.Work);
        converter = new Field007Converter(model, record);
    }

    @After
    public void tearDown() throws Exception {
        model.close();
        model = null;
        converter = null;
    }

    @Test
    public void testConvertWorkType() throws Exception {
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

    /**
     * Field 007 axxxaxxxx
     * position 00 = a
     * position 04 = a
     * @throws Exception
     */
    @Test
    public void testConvertWorkA4A() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("007")) {
                String data = field.getData();
                if (data.startsWith("a") && data.substring(4, 5).equals("a")) {
                    model = converter.convert(field);
                    Resource work = ModelUtils.getWork(model, record);
                    StmtIterator iter = model.listStatements(work, BIB_FRAME.baseMaterial, model.createResource("http://id.loc.gov/vocabulary/mmaterial/pap"));
                    assertTrue(iter.hasNext());
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    /**
     * Field 007 gxxcxxxxx
     * position 00 = g
     * position 03 = c
     * @throws Exception
     */
    @Test
    public void testConvertWorkG3C() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("007")) {
                String data = field.getData();
                if (data.startsWith("g") && data.substring(3, 4).equals("c")) {
                    model = converter.convert(field);
                    Resource work = ModelUtils.getWork(model, record);
                    StmtIterator iter = model.listStatements(work, BIB_FRAME.colorContent, model.createResource("http://id.loc.gov/vocabulary/mcolor/mul"));
                    assertTrue(iter.hasNext());
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    /**
     * Field 007 khxxxxxxx
     * position 00 = k
     * position 01 = h
     * @throws Exception
     */
    @Test
    public void testConvertWorkK1H() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("007")) {
                String data = field.getData();
                if (data.startsWith("k") && data.substring(1, 2).equals("h")) {
                    model = converter.convert(field);
                    Resource work = ModelUtils.getWork(model, record);
                    StmtIterator iter = model.listStatements(work, BIB_FRAME.genreForm, model.createResource("http://id.loc.gov/vocabulary/graphicMaterials/tgm007718"));
                    assertTrue(iter.hasNext());
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    /**
     * Field 007 mxxxxxxxxaxxx
     * position 00 = m
     * position 09 = a
     * @throws Exception
     */
    @Test
    public void testConvertWorkM9A() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("007")) {
                String data = field.getData();
                if (data.startsWith("m") && data.substring(9, 10).equals("a")) {
                    model = converter.convert(field);
                    Resource work = ModelUtils.getWork(model, record);
                    assertTrue(TestUtils.checkWorkLabel(work, BIB_FRAME.genreForm, "workprint"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }
    /**
     * Field 007 vxxcxxxxx
     * position 00 = v
     * position 03 = c
     * @throws Exception
     */
    @Test
    public void testConvertWorkV3C() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("007")) {
                String data = field.getData();
                if (data.startsWith("v") && data.substring(3, 4).equals("c")) {
                    model = converter.convert(field);
                    Resource work = ModelUtils.getWork(model, record);
                    StmtIterator iter = model.listStatements(work, BIB_FRAME.colorContent, model.createResource("http://id.loc.gov/vocabulary/mcolor/mul"));
                    assertTrue(iter.hasNext());
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testConvertDeafultGenreForm() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("007")) {
                if (field.getData().startsWith("d")) {
                    model = converter.convert(field);
                    Resource work = ModelUtils.getWork(model, record);
                    StmtIterator iter = model.listStatements(work, BIB_FRAME.genreForm, model.createResource("http://id.loc.gov/vocabulary/marcgt/glo"));
                    assertTrue(iter.hasNext());
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }
}
