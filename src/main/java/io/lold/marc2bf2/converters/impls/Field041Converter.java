package io.lold.marc2bf2.converters.impls;

import io.lold.marc2bf2.converters.FieldConverter;
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

public class Field041Converter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(Field041Converter.class);


    public Field041Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        Resource work = ModelUtils.getWork(model, record);
        DataField df = (DataField) field;

        for (Subfield sf: df.getSubfields("abdefghjkmn")) {
            char c = sf.getCode();
            String part = getPart(c);
            if (df.getIndicator2() == ' ') { // marc language code
                String[] langs = sf.getData().split("(?<=\\G.{3})");
                for (String lang: langs) {
                    String uri = ModelUtils.getUriWithNsPrefix("languages", lang);
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
            work.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, "Includes translation"));
        }

        return model;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "041".equals(getTag(field));
    }
    
    protected String getPart(char code) {
        switch (code) {
            case 'b': return "summary";
            case 'd': return "sung or spoken text";
            case 'e': return "libretto";
            case 'f': return "table of contents";
            case 'g': return "accompanying material";
            case 'h': return "original";
            case 'j': return "subtitles or captions";
            case 'k': return "intermediate translations";
            case 'm': return "original accompanying materials";
            case 'n': return "original libretto";
            default : return null;
        }
    }
}
