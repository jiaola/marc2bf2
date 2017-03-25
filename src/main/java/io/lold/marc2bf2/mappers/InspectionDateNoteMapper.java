package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.impl.XSDYearMonthType;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InspectionDateNoteMapper extends DefaultMapper {
    public InspectionDateNoteMapper(Model model) {
        super(model);
    }

    private String mapToLabel(String value) {
        switch (value) {
            case "||||||":
            case "------":
                return null;
            default:
                return value.substring(0, 4) + "-" + value.substring(4);
        }
    }

    @Override
    public List<RDFNode> map(String value, Map<String, Object> config, Map<String, Object> mapping) throws Exception {
        List<RDFNode> list = new ArrayList<>();
        String label = mapToLabel(value);
        if (label == null) {
            return list;
        }
        Literal literal = model.createTypedLiteral(label, new XSDYearMonthType("gYearMonth"));
        RDFNode object = model.createResource(BIB_FRAME.Note)
                .addProperty(BIB_FRAME.noteType, "film inspection date")
                .addProperty(RDFS.label, literal);
        list.add(object);
        return list;
    }
}
