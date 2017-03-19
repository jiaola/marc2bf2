package io.lold.marc2bf2.converters;

import org.apache.jena.rdf.model.Model;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public class Field007Converter extends FieldConverter {
    public Field007Converter(Model model, Record record) {
        super(model, record);
    }

    public Model convert(VariableField field) {
        if (!field.getTag().equals("007")) {
            return null;
        }
        // 1 position
        char[] chars = ((ControlField)field).getData().toCharArray();
        switch (chars[0]) {
            case 'a': // Map
                break;
            default:
                break;
        }
        return null;
    }

}
