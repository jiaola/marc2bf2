package io.lold.marc2bf2.converters.field001to007;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Field005Converter extends FieldConverter {
    public Field005Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyyMMddHHmmss");

        try {
            Date date = parser.parse(((ControlField)field).getData().substring(0, 14));
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.setTimeZone(TimeZone.getTimeZone("EST"));
            calendar.set(Calendar.MILLISECOND, 0);
            Literal literal = model.createTypedLiteral(calendar);
            Resource amd = ModelUtils.getAdminMatadata(model, record);
            amd.addProperty(BIB_FRAME.changeDate, literal);
        } catch (ParseException ex) {}
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "005".equals(field.getTag());
    }
}
