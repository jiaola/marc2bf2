package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.ControlField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Conversion Specs for MARC 001-007 fields
 */
public class Marc001To007Converter {
    private Model model;
    public Marc001To007Converter(Model model) {
        super();
        this.model = model;
    }

    public RDFNode convert001(ControlField field) {
        if (!field.getTag().equals("001")) {
            return null;
        }
        return model.createResource().
                addProperty(RDF.type, BIB_FRAME.Local).
                addProperty(RDF.value, field.getData());
    }

    public RDFNode convert003(ControlField field) {
        if (!field.getTag().equals("003")) {
            return null;
        }
        return model.createResource().
                addProperty(RDF.type, BIB_FRAME.Source).
                addProperty(BIB_FRAME.code, field.getData());
    }

    public RDFNode convert005(ControlField field) {
        if (!field.getTag().equals("005")) {
            return null;
        }
        SimpleDateFormat parser = new SimpleDateFormat("yyyymmddHHmmss");

        try {
            Date date = parser.parse(field.getData().substring(0, 14));
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            return model.createTypedLiteral(calendar);
        } catch (ParseException ex) {}
        return null;
    }
}
