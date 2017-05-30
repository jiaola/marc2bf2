package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Field536Converter extends Field500Converter {
    private Map<Character, String> codePrefix;
    public Field536Converter(Model model, Record record) {
        super(model, record);
        this.codePrefix = Collections.unmodifiableMap(Stream.of(
                new AbstractMap.SimpleEntry<>('b', "Contract"),
                new AbstractMap.SimpleEntry<>('c', "Grant"),
                new AbstractMap.SimpleEntry<>('e', "Program element"),
                new AbstractMap.SimpleEntry<>('f', "Project"),
                new AbstractMap.SimpleEntry<>('g', "Task"),
                new AbstractMap.SimpleEntry<>('h', "Work unit"))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        Resource note = buildResource(df, BIB_FRAME.Note).addProperty(BIB_FRAME.noteType, "funding information");

        for (Subfield sf: df.getSubfields("bcdefgh")) {
            String value = sf.getData();
            if (codePrefix.containsKey(sf.getCode())) {
                value = codePrefix.get(sf.getCode()) + ": " + value;
            }
            note.addProperty(RDFS.label, createLiteral(value, lang));
        }
        instance.addProperty(BIB_FRAME.note, note);
        return model;
    }


    @Override
    public boolean checkField(VariableField field) {
        return "536".equals(getTag(field));
    }
}
