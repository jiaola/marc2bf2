package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.ModelFactory;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Field041Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field041Converter.class);


    public Field041Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    public Model convert(VariableField field) throws Exception {
        if (!field.getTag().equals("041")) {
            return model;
        }
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;

        Map<Character, String> partMap = Collections.unmodifiableMap(Stream.of(
                new SimpleEntry<>('b', "summary"),
                new SimpleEntry<>('d', "sung or spoken text"),
                new SimpleEntry<>('e', "libretto"),
                new SimpleEntry<>('f', "table of contents"),
                new SimpleEntry<>('g', "accompanying material"),
                new SimpleEntry<>('h', "original"),
                new SimpleEntry<>('j', "subtitles or captions"),
                new SimpleEntry<>('k', "intermediate translations"),
                new SimpleEntry<>('m', "original accompanying materials"),
                new SimpleEntry<>('n', "original libretto"))
                .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));

        char[] subfields = {'a', 'b', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n'};
        for (char c: subfields) {
            String part = null;
            Subfield sf = df.getSubfield(c);
            if (sf == null) continue;
            part = partMap.get(c);
            if (df.getIndicator2() == ' ') { // marc language code
                String[] langs = sf.getData().split("(?<=\\G.{3})");
                for (String lang: langs) {
                    String uri = ModelFactory.prefixMapping().getNsPrefixURI("languages") + lang;
                    Resource language = model.createResource(uri)
                            .addProperty(RDF.type, BIB_FRAME.Language)
                            .addProperty(BIB_FRAME.source, model.createResource("http://id.loc.gov/vocabulary/languages")
                                    .addProperty(RDF.type, BIB_FRAME.Source)
                            );
                    if (part != null) language.addProperty(BIB_FRAME.part, part);
                    work.addProperty(BIB_FRAME.language, language);
                }
            } else if (df.getIndicator2() == '7') {
                Resource language = model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Language)
                        .addProperty(RDFS.label, sf.getData());
                Subfield two = df.getSubfield('2');
                if (two != null) {
                    String source = two.getData();
                    if ("iso639-1".equals(source)) {
                        String uri = "http://id.loc.gov/vocabulary/iso639-1";
                        language.addProperty(BIB_FRAME.source, model.createResource(uri)
                                .addProperty(RDF.type, BIB_FRAME.Source));
                    } else if (StringUtils.isNotBlank(source)) {
                        language.addProperty(BIB_FRAME.source, model.createResource()
                                .addProperty(RDF.type, BIB_FRAME.Source)
                                .addProperty(RDFS.label, source));
                    }
                }
                if (part != null) language.addProperty(BIB_FRAME.part, part);
                work.addProperty(BIB_FRAME.language, language);
            }
        }

        if (df.getIndicator1() == '1') {
            work.addProperty(BIB_FRAME.note, ModelUtils.createNote(model, "Includes translation"));
        }

        return model;
    }
}