package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.query.*;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class Field006008ConverterTest {
    private Field006008Converter converter;
    private Model model;

    @Parameterized.Parameter
    public Record record;

    @Parameterized.Parameters
    public static Record[] records() {
        return TestUtils.readTestRecords("ConvSpec-006,008/marc.xml");
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

        converter = new Field006008Converter(model, record);
    }


    @After
    public void tearDown() throws Exception {
        model.close();
        model = null;
        converter = null;
    }

    @Test
    public void testWorkCreationDate() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008")) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                Resource adm = ModelUtils.getAdminMatadata(model, record);
                Statement stmt = adm.getProperty(BIB_FRAME.creationDate);
                Literal literal = stmt.getLiteral();
                if (data.substring(0, 6).equals("040520")) {
                    assertEquals("http://www.w3.org/2001/XMLSchema#date", literal.getDatatypeURI());
                    assertEquals("2004-05-20", literal.getString());
                } else if (data.substring(0, 6).equals("830317")) {
                    assertEquals("http://www.w3.org/2001/XMLSchema#date", literal.getDatatypeURI());
                    assertEquals("1983-03-17", literal.getString());
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    // Test the NoteTypeMapper
    @Test
    public void testAdminMetadataEntryConvention() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isContinuingResourceByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                if (data.substring(34, 35).equals("0")) {
                    Resource adm = ModelUtils.getAdminMatadata(model, record);
                    assertTrue(TestUtils.checkPropertyLabel(adm, BIB_FRAME.note, "0 - successive"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testWorkIntendedAudience() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isBookByLeader(record)) {
                String data = field.getData();
                if (data.substring(22, 23).equals("j")) {
                    model = converter.convert(field);
                    model.write(System.out);
                    Resource work = ModelUtils.getWork(model, record);
                    assertTrue(TestUtils.checkPropertyLabel(work, BIB_FRAME.intendedAudience, "Juvenile"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testWorkBookGovdoc() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isBookByLeader(record)) {
                String data = field.getData();
                if (data.substring(28, 29).equals("a")) {
                    model = converter.convert(field);
                    model.write(System.out);
                    Resource work = ModelUtils.getWork(model, record);
                    assertTrue(TestUtils.checkPropertyLabel(work, BIB_FRAME.genreForm, "autonomous or semi-autonomous government publication"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }


    @Test
    public void testWorkBookGenreForm() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isBookByLeader(record)) {
                String data = field.getData();
                if (data.substring(24, 25).equals("6")) {
                    model = converter.convert(field);
                    model.write(System.out);
                    Resource work = ModelUtils.getWork(model, record);
                    assertTrue(TestUtils.checkPropertyLabel(work, BIB_FRAME.genreForm, "comic or graphic novel"));
                    assertTrue(TestUtils.checkPropertyResourceURI(work, BIB_FRAME.genreForm, "http://id.loc.gov/vocabulary/marcgt/cgn"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testWorkBookConference() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isBookByLeader(record)) {
                String data = field.getData();
                if (data.substring(29, 30).equals("1")) {
                    model = converter.convert(field);
                    model.write(System.out);
                    Resource work = ModelUtils.getWork(model, record);
                    assertTrue(TestUtils.checkPropertyLabel(work, BIB_FRAME.genreForm, "conference publication"));
                    assertTrue(TestUtils.checkPropertyResourceURI(work, BIB_FRAME.genreForm, "http://id.loc.gov/vocabulary/marcgt/cpb"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testWorkBookLitForm() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isBookByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                Resource work = ModelUtils.getWork(model, record);
                if (data.substring(33, 34).equals("1")) {
                    assertTrue(TestUtils.checkPropertyLabel(work, BIB_FRAME.genreForm, "fiction"));
                    assertTrue(TestUtils.checkPropertyResourceURI(work, BIB_FRAME.genreForm, "http://id.loc.gov/vocabulary/marcgt/fic"));
                } else if (data.substring(33, 34).equals("d")) {
                    assertTrue(TestUtils.checkPropertyLabel(work, BIB_FRAME.genreForm, "drama"));
                    assertTrue(TestUtils.checkPropertyResourceURI(work, BIB_FRAME.genreForm, "http://id.loc.gov/vocabulary/marcgt/dra"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testWorkComputerFileType() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isComputerFileByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                Resource work = ModelUtils.getWork(model, record);
                if (data.substring(26, 27).equals("m")) {
                    assertTrue(TestUtils.checkPropertyLabel(work, BIB_FRAME.genreForm, "computer file combination"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testWorkMusicSuppContent() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isMusicByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                Resource work = ModelUtils.getWork(model, record);
                if (data.substring(24, 25).equals("a")) {
                    assertTrue(TestUtils.checkPropertyLabel(work, BIB_FRAME.supplementaryContent, "discography"));
                }
                if (data.substring(25, 26).equals("b")) {
                    assertTrue(TestUtils.checkPropertyLabel(work, BIB_FRAME.supplementaryContent, "bibliography"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testWorkMusicCompform() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isMusicByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                Resource work = ModelUtils.getWork(model, record);
                if (data.substring(18, 20).equals("hy")) {
                    assertTrue(TestUtils.checkPropertyLabel(work, BIB_FRAME.genreForm, "hymns"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testWorkContinuingResourceScript() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isContinuingResourceByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                Resource work = ModelUtils.getWork(model, record);
                if (data.substring(33, 34).equals("a")) {
                    assertTrue(TestUtils.checkPropertyLabel(work, BIB_FRAME.notation, "basic roman"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testWorkVisualMaterialDuration() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isVisualMaterialByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                Resource work = ModelUtils.getWork(model, record);
                if (data.substring(18, 21).equals("161")) {
                    Statement stmt = work.getProperty(BIB_FRAME.duration);
                    Literal literal = stmt.getLiteral();
                    assertEquals("http://www.w3.org/2001/XMLSchema#duration", literal.getDatatypeURI());
                    assertEquals("161", literal.getString());
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testInstanceBookIllustrativeContent() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isBookByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                Resource instance = ModelUtils.getInstance(model, record);
                if (data.substring(18, 19).equals("a")) {
                    assertTrue(TestUtils.checkPropertyResourceURI(instance, BIB_FRAME.illustrativeContent, "http://id.loc.gov/vocabulary/millus/ill"));
                    assertTrue(TestUtils.checkPropertyLabel(instance, BIB_FRAME.illustrativeContent, "Illustrations"));
                }
                if (data.substring(19, 20).equals("b")) {
                    assertTrue(TestUtils.checkPropertyResourceURI(instance, BIB_FRAME.illustrativeContent, "http://id.loc.gov/vocabulary/millus/map"));
                    assertTrue(TestUtils.checkPropertyLabel(instance, BIB_FRAME.illustrativeContent, "Maps"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testInstanceBookCarrierPrintNotation() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isBookByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                Resource instance = ModelUtils.getInstance(model, record);
                if (data.substring(23, 24).equals("o")) {
                    assertTrue(TestUtils.checkPropertyResourceURI(instance, BIB_FRAME.carrier, "http://id.loc.gov/vocabulary/carriers/cr"));
                    assertTrue(TestUtils.checkPropertyLabel(instance, BIB_FRAME.carrier, "online resource"));
                }
                if (data.substring(23, 24).equals("s")) {
                    assertTrue(TestUtils.checkPropertyLabel(instance, BIB_FRAME.carrier, "electronic"));
                }
                if (data.substring(23, 24).equals("d")) {
                    assertTrue(TestUtils.checkPropertyLabel(instance, BIB_FRAME.fontSize, "large print"));
                }
                if (data.substring(23, 24).equals("f")) {
                    assertTrue(TestUtils.checkPropertyLabel(instance, BIB_FRAME.notation, "braille"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testInstanceSupplementaryContent() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isBookByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                Resource instance = ModelUtils.getInstance(model, record);
                if (data.substring(31, 32).equals("1")) {
                    assertTrue(TestUtils.checkPropertyLabel(instance, BIB_FRAME.supplementaryContent, "Index present"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testInstanceComputerFileCarrier() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isComputerFileByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                Resource instance = ModelUtils.getInstance(model, record);
                if (data.substring(23, 24).equals("o")) {
                    assertTrue(TestUtils.checkPropertyResourceURI(instance, BIB_FRAME.carrier, "http://id.loc.gov/vocabulary/carriers/cr"));
                    assertTrue(TestUtils.checkPropertyLabel(instance, BIB_FRAME.carrier, "online resource"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testInstanceMapCartographic() throws Exception {
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isMapByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                Resource instance = ModelUtils.getInstance(model, record);
                if (data.substring(22, 24).equals("bk")) {
                    assertTrue(TestUtils.checkPropertyLabel(instance, BIB_FRAME.projection, "Krovak"));
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testInstanceMapCartographicAttributes() throws Exception {
        String q = String.join("\n"
                , "PREFIX bf: <" + BIB_FRAME.NAMESPACE + ">"
                , "PREFIX rdfs: <" + RDFS.getURI() + ">"
                , "PREFIX rdf: <" + RDF.getURI() + ">"
                , "SELECT ?note "
                , "WHERE { "
                , "  ?instance rdf:type bf:Instance ."
                , "  ?instance bf:cartographicAttributes ?carto ."
                , "  ?carto bf:note ?note ."
                , "  ?note rdfs:label \"%1s\" ."
                , "  ?x rdf:type bf:Note ."
                , "}");
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isMapByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                if (data.substring(18, 19).equals("a")) {
                    Query query = QueryFactory.create(String.format(q, "contours"));
                    QueryExecution qexec = QueryExecutionFactory.create(query, model);
                    ResultSet results = qexec.execSelect() ;
                    assertTrue(results.hasNext());
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }


    @Test
    public void testInstanceContinuingResourceRegulatory() throws Exception {
        String q = String.join("\n"
                , "PREFIX bf: <" + BIB_FRAME.NAMESPACE + ">"
                , "PREFIX rdfs: <" + RDFS.getURI() + ">"
                , "PREFIX rdf: <" + RDF.getURI() + ">"
                , "SELECT ?freq "
                , "WHERE { "
                , "  ?instance rdf:type bf:Instance ."
                , "  ?instance bf:frequency ?freq ."
                , "  ?freq rdfs:label \"%1s\" ."
                , "  ?x rdf:type bf:Frequency ."
                , "}");
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008") && RecordUtils.isContinuingResourceByLeader(record)) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                if (data.substring(19, 20).equals("n")) {
                    Query query = QueryFactory.create(String.format(q, "normalized irregular"));
                    QueryExecution qexec = QueryExecutionFactory.create(query, model);
                    ResultSet results = qexec.execSelect() ;
                    assertTrue(results.hasNext());
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }

    @Test
    public void testInstanceProvision() throws Exception {
        String q = String.join("\n"
                , "PREFIX bf: <" + BIB_FRAME.NAMESPACE + ">"
                , "PREFIX rdf: <" + RDF.getURI() + ">"
                , "SELECT ?prov  ?date "
                , "WHERE { "
                , "  ?instance rdf:type bf:Instance ."
                , "  ?instance bf:provisionActivity ?prov ."
                , "  ?prov bf:date ?date ."
                , "}");
        List<ControlField> controlFields = record.getControlFields();
        for (ControlField field: controlFields) {
            if (field.getTag().equals("008")) {
                String data = field.getData();
                model = converter.convert(field);
                model.write(System.out);
                if (data.substring(6, 7).equals("m") && data.substring(7, 15).equals("19949999")) {
                    Query query = QueryFactory.create(q);
                    QueryExecution qexec = QueryExecutionFactory.create(query, model);
                    ResultSet results = qexec.execSelect() ;
                    assertTrue(results.hasNext());
                    QuerySolution soln = results.nextSolution();
                    Literal l = soln.getLiteral("date");
                    assertEquals("1994/9999^^http://id.loc.gov/datatypes/edtf", l.toString());
                }
            } else {
                assertEquals(model, converter.convert(field)); // model shouldn't be changed
            }
        }
    }
}
