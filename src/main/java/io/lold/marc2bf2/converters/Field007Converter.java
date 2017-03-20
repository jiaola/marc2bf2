package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field007Converter extends FieldConverter {
    public Field007Converter(Model model, Record record) {
        super(model, record);
    }

    public Model convert(VariableField field) {
        if (!field.getTag().equals("007")) {
            return model;
        }
        // 1 position
        char[] chars = ((ControlField)field).getData().toCharArray();
        switch (chars[0]) {
            case 'a': // Map
                break;
            default:
                break;
        }
        model = workType();
        return model;
    }

    public Model workType() {
        System.out.println("work type");
        char type = record.getLeader().getTypeOfRecord();
//        if (type == 'e' || type == 'f') {
//            return model;
//        }
        Resource work = ModelUtils.getWork(model, record);
        work.addProperty(RDF.type, BIB_FRAME.Cartography);
        return model;
    }

}
