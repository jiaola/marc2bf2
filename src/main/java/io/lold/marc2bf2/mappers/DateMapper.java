package io.lold.marc2bf2.mappers;

import org.apache.jena.datatypes.xsd.impl.XSDDateType;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DateMapper extends Mapper {
    public DateMapper(Model model) {
        super(model);
    }

    @Override
    public List<RDFNode> map(String value, Map<String, Object> mapping) throws Exception {
        List<RDFNode> list = new ArrayList<>();
        if (value.length() != 6) {
            return list;
        }
        String year = value.substring(0, 2);
        year = Integer.valueOf(year) < 50 ? "20" + year : "19" + year;
        String month = value.substring(2, 4);
        String day = value.substring(4, 6);
        Literal literal = model.createTypedLiteral(year + "-" + month + "-" + day, new XSDDateType("date"));
        list.add(literal);
        return list;
    }
}
