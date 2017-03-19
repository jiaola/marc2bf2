package io.lold.marc2bf2.converters;

import org.apache.jena.rdf.model.Model;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

public abstract class FieldConverter {
    protected Model model;
    protected Record record;
    public FieldConverter(Model model, Record record) {
        this.model = model;
        this.record = record;
    }

    public abstract Model convert(VariableField field);
}
