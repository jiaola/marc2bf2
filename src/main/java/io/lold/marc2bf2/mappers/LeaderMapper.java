package io.lold.marc2bf2.mappers;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;

public class LeaderMapper extends DefaultMapper {
    public LeaderMapper(Model model) {
        super(model);
    }

    @Override
    protected Property getLabelProeprty() {
        return BIB_FRAME.code;
    }

}
