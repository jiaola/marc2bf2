package io.lold.marc2bf2;

import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;

public class MarcXML2Mrk {
    public static void main(String[] args) {
        String file = "marc-lang.xml";
        Record[] records = TestUtils.readTestRecords(file);
        MrkStreamWriter writer = new MrkStreamWriter(System.out);
        //MarcXmlWriter writer = new MarcXmlWriter(System.out);
        for (Record record: records) {
            writer.write(record);
        }
        writer.close();


        MrkStreamReader reader = new MrkStreamReader(null);
        DataField field = reader.parseLineToDataField("=344  \\\\$3audio disc$adigital$boptical$gsurround$hDolby Digital 5.1$2rda");
        System.out.println(field.toString());
    }
}
