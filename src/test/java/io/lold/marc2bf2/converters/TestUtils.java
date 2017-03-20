package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;
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
}
