package io.lold.marc2bf2;

import io.lold.marc2bf2.converters.*;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
    public Model convert(Record record) {
        // One model per record
        Model model = ModelFactory.createBfModel();
        ControlField cnf = record.getControlNumberField();
        if (cnf == null) {
            // no identifier
        }
        String workUri = ModelUtils.getUri(record, "Work");
        Resource work = model.createResource(workUri);

        String instanceUri = ModelUtils.getUri(record, "Instance");
        Resource instance = model.createResource(instanceUri);

        // Create AdminMetadata resource
        Resource amd = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.AdminMetadata);

        work = work.addProperty(BIB_FRAME.adminMetadata, amd);

        List<VariableField> fields = record.getVariableFields();
        for (VariableField field: fields) {
            if (field.getTag().equals("001")) {
                model = new Field001Converter(model, record).convert(field);
            } else if (field.getTag().equals("003")) {
                model = new Field003Converter(model, record).convert(field);
            } else if (field.getTag().equals("005")) {
                model = new Field005Converter(model, record).convert(field);
            } else if (field.getTag().equals("007")) {
                Field007Converter converter = null;
                try {
                    converter = new Field007Converter(model, record);
                    model = converter.convert(field);
                } catch (Exception ex) {
                    logger.error("Failed to create Field007Converter", ex);
                }

            }
        }

        // Keep this line at the end; Otherwise Jena won't use bf:Work as the root tag in RDF/XML.
        work.addProperty(RDF.type, BIB_FRAME.Work);
        instance.addProperty(RDF.type, BIB_FRAME.Instance);
        return model;
    }
}
