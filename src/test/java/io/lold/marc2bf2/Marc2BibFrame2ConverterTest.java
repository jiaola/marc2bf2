package io.lold.marc2bf2;

import org.apache.jena.rdf.model.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.Record;

import java.io.InputStream;
import java.util.ArrayList;

@RunWith(Parameterized.class)
public class Marc2BibFrame2ConverterTest {
    private Marc2BibFrame2Converter converter;
    @Parameterized.Parameter
    public Record record;

    @Parameterized.Parameters
    public static Record[] records() {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("collection.xml");
        MarcXmlReader reader = new MarcXmlReader(input);
        ArrayList<Record> records = new ArrayList<>();
        while (reader.hasNext()) {
            Record record = reader.next();
            records.add(record);
        }
        return records.toArray(new Record[records.size()]);
    }

    @Before
    public void setUp() throws Exception {
        converter = new Marc2BibFrame2Converter();
    }

    @After
    public void tearDown() throws Exception {
        converter = null;
    }

    @Test
    public void testToWork() throws Exception {
        Model model = converter.toWork(record);
        model.write(System.out);
    }
}
