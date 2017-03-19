package io.lold.marc2bf2.converters;

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
}
