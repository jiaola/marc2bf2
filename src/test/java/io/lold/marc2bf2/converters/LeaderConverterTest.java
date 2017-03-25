package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Leader;
import org.marc4j.marc.Record;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class LeaderConverterTest {
    private LeaderConverter converter;
    private Model model;

    @Parameterized.Parameter
    public Record record;

    @Parameterized.Parameters
    public static Record[] records() {
        return TestUtils.readTestRecords("ConvSpec-LDR/marc.xml");
    }

    @Before
    public void setUp() throws Exception {
        model = io.lold.marc2bf2.ModelFactory.createBfModel();
        // create a mock work and adminmetadata
        model.createResource(ModelUtils.getUri(record, "Work"))
                .addProperty(RDF.type, BIB_FRAME.Work)
                .addProperty(BIB_FRAME.adminMetadata, model.createResource()
                                .addProperty(RDF.type, BIB_FRAME.AdminMetadata));
        model.createResource(ModelUtils.getUri(record, "Instance"))
                .addProperty(RDF.type, BIB_FRAME.Instance);
        converter = new LeaderConverter(model, record);
    }

    @After
    public void tearDown() throws Exception {
        model.close();
        model = null;
        converter = null;
    }

    @Test
    public void testConvertToStatus() throws Exception {
        Leader leader = record.getLeader();
        if (leader.getRecordStatus() == 'c') {
            model = converter.convert(leader);
            model.write(System.out);

            Resource am = ModelUtils.getAdminMatadata(model, record);
            Statement stmt = am.getProperty(BIB_FRAME.status);
            Resource res = stmt.getResource();
            stmt = res.getProperty(BIB_FRAME.code);
            String label = stmt.getLiteral().getString();

            assertEquals("c", label);
        }

    }

    @Test
    public void testAddTypeWork() throws Exception {
        Leader leader = record.getLeader();
        model = converter.convert(leader);
        model.write(System.out);
        Resource work = ModelUtils.getWork(model, record);
        if (leader.getTypeOfRecord() == 'a') {
            assertTrue(TestUtils.checkResourceType(work, BIB_FRAME.Text));
        }
        if (leader.getTypeOfRecord() == 'd') {
            assertTrue(TestUtils.checkResourceType(work, BIB_FRAME.NotatedMusic));
        }
    }

    @Test
    public void testAddTypeInstance() throws Exception {
        Leader leader = record.getLeader();
        model = converter.convert(leader);
        model.write(System.out);
        Resource instance = ModelUtils.getInstance(model, record);
        String data = leader.marshal();
        if (data.substring(8, 9).equals("a")) {
            assertTrue(TestUtils.checkResourceType(instance, BIB_FRAME.Archival));
        }
        if (data.substring(7, 8).equals("c") || data.substring(7, 8).equals("c")) {
            assertTrue(TestUtils.checkResourceType(instance, BIB_FRAME.Collection));
        }
    }

    @Test
    public void testConvertToInstance() throws Exception {
        Leader leader = record.getLeader();
        String data = leader.marshal();
        if (data.substring(7, 8).equals("m")) {
            model = converter.convert(leader);
            model.write(System.out);
            Resource instance = ModelUtils.getInstance(model, record);
            StmtIterator iter = model.listStatements(instance, BIB_FRAME.issuance, model.createResource("http://id.loc.gov/vocabulary/issuance/mono"));
            assertTrue(iter.hasNext());
        }

    }
}
