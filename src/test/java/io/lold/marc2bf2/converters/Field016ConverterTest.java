package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.TestUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.jena.query.ResultSet;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class Field016ConverterTest {
    Field016Converter converter;
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
        model.createResource(ModelUtils.buildUri(record, "Work"))
                .addProperty(RDF.type, BIB_FRAME.Work)
                .addProperty(BIB_FRAME.adminMetadata, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.AdminMetadata));
        model.createResource(ModelUtils.buildUri(record, "Instance"))
                .addProperty(RDF.type, BIB_FRAME.Instance);
        converter = new Field016Converter(model, record);
    }

    @After
    public void tearDown() throws Exception {
        model.close();
        model = null;
        converter = null;
    }

    @Test
    public void testConvert() throws Exception {
        String q = String.join("\n"
                , "PREFIX bf: <" + BIB_FRAME.getURI() + ">"
                , "PREFIX rdf: <" + RDF.getURI() + ">"
                , "PREFIX rdfs: <" + RDFS.getURI() + ">"
                , "PREFIX bflc: <" + BIB_FRAME_LC.getURI() + ">"
                , "SELECT ?w  "
                , "WHERE { "
                , "  ?w bf:identifiedBy ?a ."
                , "  ?a rdf:type bf:Nbn ."  //TODO: missing bf:Nban
                , "  ?a rdf:value \"PTBN000004618\" ."
                , "}");
        List<DataField> fields = record.getDataFields();
        for (DataField field: fields) {
            if (field.getTag().equals("016") && field.getIndicator1() == '7') {
                model = converter.convert(field);
                model.write(System.out);
                ResultSet rs = TestUtils.sparql(q, model);
                assertTrue(rs.hasNext());
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }
}
