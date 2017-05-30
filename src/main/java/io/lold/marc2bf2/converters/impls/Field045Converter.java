package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.DataTypes;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Field045Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field045Converter.class);

    public Field045Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;
        List<Subfield> sfas = df.getSubfields('a');
        for (Subfield a: sfas) {
            String v = format045a(a.getData());
            if (StringUtils.isNotBlank(v)) {
                work.addProperty(BIB_FRAME.temporalCoverage, model.createTypedLiteral(v, DataTypes.EDTF));
            } else {
                logger.warn("Record " + record.getControlNumber() + ":  Failed to convert 045$a to date string: " + a.getData());
            }
        }

        List<Subfield> sfbs = df.getSubfields('b');
        if (df.getIndicator1() == '2' && sfbs.size() >= 2) {
            String v0 = format045b(sfbs.get(0).getData());
            String v1 = format045b(sfbs.get(1).getData());
            work.addProperty(BIB_FRAME.temporalCoverage, model.createTypedLiteral(v0+"/"+v1, DataTypes.EDTF));
        } else {
            for (Subfield b: sfbs) {
                String v = format045b(b.getData());
                work.addProperty(BIB_FRAME.temporalCoverage, model.createTypedLiteral(v, DataTypes.EDTF));
            }
        }

        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "045".equals(getTag(field));
    }

    protected String format045a(String data) {
        if (data.length() < 4) {
            return null;
        }
        String date1;
        if ("abced".contains(StringUtils.substring(data, 0, 1))) {
            date1 = getTime(StringUtils.substring(data, 0, 2));
        } else {
            date1 = getTime(StringUtils.substring(data, 0, 1)) + StringUtils.substring(data, 1, 2) + "X";
        }
        String date2;
        if ("abced".contains(StringUtils.substring(data, 2, 3))) {
            date2 = getTime(StringUtils.substring(data, 2, 4));
        } else {
            date2 = getTime(StringUtils.substring(data, 2, 3)) + StringUtils.substring(data, 3, 4) + "X";
        }
        if (date1.equals(date2)) {
            return date1;
        } else {
            return date1 + "/" + date2;
        }
    }

    protected String getTime(String code) {
        switch (code) {
            case "a0": return"-XXXX/-3000";
            case "b0": return"-29XX";
            case "b1": return"-28XX";
            case "b2": return"-27XX";
            case "b3": return"-26XX";
            case "b4": return"-25XX";
            case "b5": return"-24XX";
            case "b6": return"-23XX";
            case "b7": return"-22XX";
            case "b8": return"-21XX";
            case "b9": return"-20XX";
            case "c0": return"-19XX";
            case "c1": return"-18XX";
            case "c2": return"-17XX";
            case "c3": return"-16XX";
            case "c4": return"-15XX";
            case "c5": return"-14XX";
            case "c6": return"-13XX";
            case "c7": return"-12XX";
            case "c8": return"-11XX";
            case "c9": return"-10XX";
            case "d0": return"-09XX";
            case "d1": return"-08XX";
            case "d2": return"-07XX";
            case "d3": return"-06XX";
            case "d4": return"-05XX";
            case "d5": return"-04XX";
            case "d6": return"-03XX";
            case "d7": return"-02XX";
            case "d8": return"-01XX";
            case "d9": return"-00XX";
            case "e": return"00";
            case "f": return"01";
            case "g": return"02";
            case "h": return"03";
            case "i": return"04";
            case "j": return"05";
            case "k": return"06";
            case "l": return"07";
            case "m": return"08";
            case "n": return"09";
            case "o": return"10";
            case "p": return"11";
            case "q": return"12";
            case "r": return"13";
            case "s": return"14";
            case "t": return"15";
            case "u": return"16";
            case "v": return"17";
            case "w": return"18";
            case "x": return"19";
            case "y": return"20";
            default: return null;
        }
    }

    protected String format045b(String data) {
        String date = StringUtils.substring(data, 1, 5);
        if (data.startsWith("c")) {
            date = "-" + date;
        }
        String month = StringUtils.substring(data, 5, 7);
        if (StringUtils.isNotBlank(month)) {
            date = date + "-" + month;
        }
        String day = StringUtils.substring(data, 7, 9);
        if (StringUtils.isNotBlank(day)) {
            date = date + "-" + day;
        }
        String hour = StringUtils.substring(data, 9, 11);
        if (StringUtils.isNotBlank(hour)) {
            date = date + "%" + hour;
        }
        return date;
    }
}
