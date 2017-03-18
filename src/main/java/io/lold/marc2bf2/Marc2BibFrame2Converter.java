package io.lold.marc2bf2;

import io.lold.marc2bf2.converters.Marc001To007Converter;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Marc2BibFrame2Converter {
    public Marc2BibFrame2Converter() {
        super();
    }

    public static final int WORK = 0;
    public static final int INSTANCE = 1;

    /**
     * Convert a record to bf:work model
     * @param record
     * @return
     */
    public Model toWork(Record record) {
        // One model per record
        Model model = ModelFactory.createBfModel();
        ControlField cnf = record.getControlNumberField();
        if (cnf == null) {
            // no identifier
        }
        String workUri = "http://example.org/" + cnf.getData() + "#Work";

        Resource work = model.createResource(workUri)
                .addProperty(RDF.type, BIB_FRAME.Work);

        // Create AdminMetadata resource
        Resource amd = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.AdminMetadata);

        work = work.addProperty(BIB_FRAME.adminMetadata, amd);

        List<VariableField> fields = record.getVariableFields();
        for (VariableField field: fields) {
            if (field.getTag().equals("001")) {
                RDFNode node = new Marc001To007Converter(model).convert001((ControlField)field);
                amd.addProperty(BIB_FRAME.identifiedBy, node);
            } else if (field.getTag().equals("003")) {
                RDFNode node = new Marc001To007Converter(model).convert003((ControlField)field);
                amd.addProperty(BIB_FRAME.source, node);
            } else if (field.getTag().equals("005")) {
                RDFNode node = new Marc001To007Converter(model).convert005((ControlField)field);
                amd.addProperty(BIB_FRAME.changeDate, node);
            }
        }
        return model;
    }
}
