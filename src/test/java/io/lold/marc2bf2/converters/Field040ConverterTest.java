package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class Field040ConverterTest {
    Field040Converter converter;
    private Model model;

    @Parameterized.Parameter
    public Record record;

    @Parameterized.Parameters
    public static Record[] records() {
        return TestUtils.readTestRecords("ConvSpec-010-048/marc.xml");
    }

    @Before
    public void setUp() {
        model = io.lold.marc2bf2.ModelFactory.createBfModel();
        // create a mock work and adminmetadata
        model.createResource(ModelUtils.getUri(record, "Work"))
                .addProperty(RDF.type, BIB_FRAME.Work)
                .addProperty(BIB_FRAME.adminMetadata, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.AdminMetadata));
        converter = new Field040Converter(model, record);
    }

    @After
    public void tearDown() throws Exception {
        model.close();
        model = null;
        converter = null;
    }

    @Test
    public void testConvertSubfieldA() throws Exception {
        String q = String.join("\n"
                , "PREFIX bf: <" + BIB_FRAME.getURI() + ">"
                , "PREFIX rdf: <" + RDF.getURI() + ">"
                , "PREFIX rdfs: <" + RDFS.getURI() + ">"
                , "PREFIX bflc: <" + BIB_FRAME_LC.getURI() + ">"
                , "SELECT ?x  ?y "
                , "WHERE { "
                , "  ?x bf:source ?y ."
                , "  ?y rdfs:label \"%1s\" ."
                , "}");
        List<DataField> fields = record.getDataFields();
        for (DataField field : fields) {
            if (field.getTag().equals("040")) {
                model = converter.convert(field);
                model.write(System.out);
                List<Subfield> subfields = field.getSubfields('a');
                for (Subfield sf : subfields) {
                    ResultSet results = TestUtils.sparql(String.format(q, sf.getData()), model);
                    assertTrue(results.hasNext());
                }
            }
        }
    }

    @Test
    public void testConvertSubfieldE() throws Exception {
        List<DataField> fields = record.getDataFields();
        for (DataField field: fields) {
            if (field.getTag().equals("040")) {
                if (!field.getSubfields('e').isEmpty()) {
                    model = converter.convert(field);
                    model.write(System.out);
                    List<Subfield> subfields = field.getSubfields('e');
                    for (Subfield sf: subfields) {
                        String q;
                        if (sf.getData().trim().contains(" ")) {
                            q = String.join("\n"
                                    , "PREFIX bf: <" + BIB_FRAME.getURI() + ">"
                                    , "PREFIX rdf: <" + RDF.getURI() + ">"
                                    , "PREFIX rdfs: <" + RDFS.getURI() + ">"
                                    , "PREFIX bflc: <" + BIB_FRAME_LC.getURI() + ">"
                                    , "SELECT ?x  ?y "
                                    , "WHERE { "
                                    , "  ?x bf:descriptionConventions ?y ."
                                    , "  ?y rdfs:label \"%1s\" ."
                                    , "}");
                            ResultSet results = TestUtils.sparql(String.format(q, sf.getData()), model);
                            assertTrue(results.hasNext());
                        } else {
                            q = String.join("\n"
                                    , "PREFIX bf: <" + BIB_FRAME.getURI() + ">"
                                    , "PREFIX rdf: <" + RDF.getURI() + ">"
                                    , "PREFIX rdfs: <" + RDFS.getURI() + ">"
                                    , "PREFIX bflc: <" + BIB_FRAME_LC.getURI() + ">"
                                    , "SELECT ?x "
                                    , "WHERE { "
                                    , "  ?x bf:descriptionConventions <http://id.loc.gov/vocabulary/descriptionConventions/%1s> ."
                                    , "}");
                            ResultSet results = TestUtils.sparql(String.format(q, sf.getData()), model);
                            assertTrue(results.hasNext());
                        }

                    }
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

}
