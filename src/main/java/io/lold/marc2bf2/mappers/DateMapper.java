package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.utils.FormatUtils;
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
        String date = FormatUtils.formatDate6d(value);
        Literal literal = model.createTypedLiteral(date, new XSDDateType("date"));
        list.add(literal);
        return list;
    }
}
