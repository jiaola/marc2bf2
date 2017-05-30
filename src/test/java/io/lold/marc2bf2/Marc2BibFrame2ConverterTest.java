package io.lold.marc2bf2;

import cucumber.deps.difflib.StringUtills;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.ResourceUtils;
import org.apache.jena.vocabulary.RDF;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.marc4j.marc.Record;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class Marc2BibFrame2ConverterTest {
    private Marc2BibFrame2Converter converter;
    private List<String> visited = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        converter = new Marc2BibFrame2Converter();
    }

    @After
    public void tearDown() throws Exception {
        converter = null;
    }

    private void renameObjects(Model model, Resource subject, String uri, int index) {
        StmtIterator si = subject.listProperties();
        List<String> types = new ArrayList<>();
        List<Resource> objects = new ArrayList<>();
        System.out.println("Subject: " + subject.getLocalName());
        while (si.hasNext()) {
            Statement stmt = si.nextStatement();
            RDFNode object = stmt.getObject();
            //System.out.println(object);
            Property property = stmt.getPredicate();
            if (property.equals(RDF.type)) {
                types.add(((Resource) object).getLocalName());
            } else if (object.isResource()) {
                if (object.isURIResource()) {
                    if (!visited.contains(((Resource)object).getURI())) {
                        objects.add((Resource) object);
                        visited.add(((Resource) object).getURI());
                    }
                } else {
                    objects.add((Resource)object);
                }
                //System.out.println("Need uri");
            }
        }
        System.out.println("Types: " + StringUtills.join(types, " "));
        if (subject.getURI() == null && !types.isEmpty()) {
            Collections.sort(types);
            uri = uri + "_" + types.get(0);
            Resource toSearch = ResourceFactory.createResource(uri);
            if (model.containsResource(toSearch)) {
                uri = uri + index;
            }
            ResourceUtils.renameResource(subject, uri);
            visited.add(uri);
        }
        for (int i = 0; i < objects.size(); i++) {
            System.out.println(objects.get(i));
            renameObjects(model, objects.get(i), uri, i);
        }
    }

    @Test
    public void testConvertCollection() throws Exception {
        Record[] records = TestUtils.readTestRecords("collection.xml");
        Model model = ModelFactory.createBfModel();

        Model expected = org.apache.jena.rdf.model.ModelFactory.createDefaultModel();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("collection.rdf");
        expected.read(input, "");

        for (Record record: records) {
            model = converter.convert(record, model);

//            // Add IRIs to the blank nodes
//            this.visited = new ArrayList<>();
//            Resource work = ModelUtils.getWork(model, record);
//            renameObjects(model, work, work.getURI(), 0);
//
//            this.visited = new ArrayList<>();
//            Resource workExpected = ModelUtils.getWork(expected, record);
//            renameObjects(expected, workExpected, workExpected.getURI(), 0);
        }
//        model.write(System.out);
//        expected.write(System.out);
//
//        Model diff =  model.difference(expected);
//        diff.write(System.out);
//
//        Model diff2 = expected.difference(model);
//        diff2.write(System.out);

        //Model inter = model.intersection(expected);
        //inter.write(System.out);

        int numOfTriples = model.listStatements().toList().size();
        int numOfTriplesExpected = expected.listStatements().toList().size();

        assertEquals(numOfTriplesExpected, numOfTriples);
        //assertTrue(model.isIsomorphicWith(expected));

    }

    @Test
    public void testSingleRecord() throws Exception {
        Record[] records = TestUtils.readTestRecords("marc.xml");
        Model model = ModelFactory.createBfModel();

        Model expected = org.apache.jena.rdf.model.ModelFactory.createDefaultModel();
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("marc.rdf");
        expected.read(input, "");

        for (Record record: records) {
            model = converter.convert(record, model);
        }

        int numOfTriples = model.listStatements().toList().size();
        int numOfTriplesExpected = expected.listStatements().toList().size();

        assertEquals(numOfTriplesExpected, numOfTriples);
    }

    @Test
    public void testBGCRecord() throws Exception {
        Record[] records = TestUtils.readTestRecords("bgc.xml");
        Model model = ModelFactory.createBfModel();
        for (Record record: records) {
            model = converter.convert(record, model);
        }
        model.write(System.out);
    }
}
