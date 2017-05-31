package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
import org.apache.jena.rdf.model.Model;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

public class Field880Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field880Converter.class);

    public Field880Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        String tag = getTag(field);
        if ("535".equals(tag)) return model;  // 535 is processs in 533/534
        String className = "io.lold.marc2bf2.converters.impls.Field" + tag + "Converter";
        try {
            Class clazz = Class.forName(className);
            Constructor<FieldConverter> cons =  clazz.getConstructor(Model.class, Record.class);
            FieldConverter converter = cons.newInstance(model, record);
            return converter.convert(field, fieldIndex);
        } catch (ClassNotFoundException ex) {
            logger.error("Converter for field 880$6" + getTag(field) + " can't be found: " + className);
        } catch (NoSuchMethodException nsme) {
            logger.error("Converter doesn't have the correct constructor: " + className);
        } catch (Exception ex) {
            logger.error("Can't create a new instance of converter for field 880$6" + getTag(field));
        }
        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "880".equals(field.getTag());
    }

}
