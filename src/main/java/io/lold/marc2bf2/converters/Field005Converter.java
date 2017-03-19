package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Field005Converter extends FieldConverter {
    public Field005Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) {
        if (!field.getTag().equals("005")) {
            return model;
        }
        SimpleDateFormat parser = new SimpleDateFormat("yyyymmddHHmmss");

        try {
            Date date = parser.parse(((ControlField)field).getData().substring(0, 14));
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            Literal literal = model.createTypedLiteral(calendar);
            Resource amd = ModelUtils.getAdminMatadata(model, record);
            amd.addProperty(BIB_FRAME.changeDate, literal);
        } catch (ParseException ex) {}
        return model;
    }
}
