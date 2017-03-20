package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.Record;

import java.io.InputStream;
import java.util.ArrayList;

public class TestUtils {
    public static Record[] readTestRecords(String file) {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        MarcXmlReader reader = new MarcXmlReader(input);
        ArrayList<Record> records = new ArrayList<>();
        while (reader.hasNext()) {
            Record record = reader.next();
            records.add(record);
        }
        return records.toArray(new Record[records.size()]);
    }

    public static boolean checkWorkType(Resource work, Resource type) {
        StmtIterator iter = work.listProperties(RDF.type);
        while (iter.hasNext()) {
            Statement stmt = iter.next();
            if (stmt.getResource().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkWorkLabel(Resource work, Property property, String label) {
        StmtIterator iter = work.listProperties(BIB_FRAME.genreForm);
        while (iter.hasNext()) {
            Statement stmt = iter.next();
            Resource resource = stmt.getResource();
            if (resource.getURI() != null) {
                continue;
            }
            stmt = resource.getProperty(RDFS.label);
            if (stmt.getLiteral().getString().equals(label)) {
                return true;
            }
        }
        return false;
    }
}
