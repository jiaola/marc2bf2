package io.lold.marc2bf2.mappers;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.datatypes.xsd.impl.XSDDurationType;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DurationMapper extends DefaultMapper {
    public DurationMapper(Model model) {
        super(model);
    }

    protected String mapToLabel(String value) {
        switch (value) {
            case "000":
                return "more than 999 minutes";
            case "nnn":
            case "---":
            case "|||":
                return null;
            default:
                return value;
        }
    }

    @Override
    public List<RDFNode> map(String value, Map<String, Object> mapping) throws Exception {
        List<RDFNode> list = new ArrayList<>();
        String label = mapToLabel(value);
        if (label == null) {
            return list;
        }
        Literal literal = StringUtils.isNumeric(value) ?
                model.createTypedLiteral("P" + label + "M", new XSDDurationType()) :
                model.createLiteral(label);
        list.add(literal);
        return list;
    }
}
