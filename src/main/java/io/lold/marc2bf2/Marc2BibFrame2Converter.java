package io.lold.marc2bf2;

import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.converters.LeaderConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.List;

public class Marc2BibFrame2Converter {
    final static Logger logger = LoggerFactory.getLogger(Marc2BibFrame2Converter.class);

    public Marc2BibFrame2Converter() {
        super();
    }

    /**
     * Convert a record to bf:work model
     * @param record
     * @return
     */
    public Model convert(Record record, Model model) throws Exception {
        String instanceUri = ModelUtils.buildUri(record, "Instance");
        Resource instance = model.createResource(instanceUri)
                .addProperty(RDF.type, BIB_FRAME.Instance);

        String workUri = ModelUtils.buildUri(record, "Work");
        Resource work = model.createResource(workUri)
                .addProperty(BIB_FRAME.adminMetadata, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.AdminMetadata));
        work.addProperty(BIB_FRAME.hasInstance, instance.addProperty(BIB_FRAME.instanceOf, work));

        LeaderConverter leaderConverter = new LeaderConverter(model, record);
        model = leaderConverter.convert(record.getLeader());
        List<VariableField> fields = record.getVariableFields();
        for (int i = 0; i < fields.size(); i++) {
            VariableField field = fields.get(i);
            String tag = "880".equals(field.getTag()) ?
                    StringUtils.substring(((DataField) field).getSubfieldsAsString("6"), 0, 3) :
                    field.getTag();
            if ("535".equals(tag)) continue;
            String className = "io.lold.marc2bf2.converters.impls.Field" + tag + "Converter";
            FieldConverter converter = null;
            try {
                Class clazz = Class.forName(className);
                Constructor<FieldConverter> cons = clazz.getConstructor(Model.class, Record.class);
                converter = cons.newInstance(model, record);
            } catch (ClassNotFoundException ex) {
                //logger.warn("Converter for field " + field.getTag() + " can't be found: " + className);
            } catch (NoSuchMethodException nsme) {
                logger.error("Converter doesn't have the correct constructor: " + className);
            } catch (Exception ex) {
                logger.error("Can't create a new instance of converter for field " + field.getTag(), ex);
            }
            if (converter != null) {
                try {
                    model = converter.convert(field, i);
                } catch (Exception ex) {
                    logger.error("Can't convert field " + field.getTag() + " in record " + record.getControlNumber());
                    ex.printStackTrace();
                }
            }
        }
        work.addProperty(RDF.type, BIB_FRAME.Work);
        return model;
    }
}
