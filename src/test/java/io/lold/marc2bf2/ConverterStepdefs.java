package io.lold.marc2bf2;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.lold.marc2bf2.converters.FieldConverter;
import io.lold.marc2bf2.converters.LeaderConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.Leader;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;
import org.marc4j.marc.impl.RecordImpl;

import java.lang.reflect.Constructor;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConverterStepdefs {
    Model model;
    Record record;
    String sparql = String.join("\n"
            , "PREFIX bf: <" + BIB_FRAME.getURI() + ">"
            , "PREFIX rdf: <" + RDF.getURI() + ">"
            , "PREFIX rdfs: <" + RDFS.getURI() + ">"
            , "PREFIX bflc: <" + BIB_FRAME_LC.getURI() + ">"
            , "SELECT %1s "
            , "WHERE { "
            , " %2s "
            , "}");
    Query query = null;

    @Before
    public void setup() {
        model = io.lold.marc2bf2.ModelFactory.createBfModel();
        record = new RecordImpl() {
            @Override
            public String getControlNumber() {
                return "9999999999";
            }
        };

        model.createResource(ModelUtils.buildUri(record, "Work"))
                .addProperty(RDF.type, BIB_FRAME.Work)
                .addProperty(BIB_FRAME.adminMetadata, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.AdminMetadata));
        model.createResource(ModelUtils.buildUri(record, "Instance"))
                .addProperty(RDF.type, BIB_FRAME.Instance);
    }

    @Given("^a marc record:$")
    public void marc_record_step(String[] lines) throws Exception {
    }

    @Given("^a marc field \"(.*)\"$")
    public void marc_field_step(String line) throws Exception {
        record.addVariableField(new MrkStreamReader().parseLineToVariableField(line));
    }

    @Given("^a marc leader (.*)$")
    public void marc_leader_step(String line) throws Exception {
        record.setLeader(new MrkStreamReader().parseLineToLeader(line));
    }

    @When("^converted by a field converter (.*)$")
    public void field_convert_step(Class cls) throws Exception {
        Constructor<FieldConverter> ctor = cls.getDeclaredConstructor(Model.class, Record.class);
        FieldConverter converter = ctor.newInstance(model, record);
        for (VariableField field: record.getVariableFields()) {
            model = converter.convert(field);
        }
    }

    @When("^converted by a leader converter (.*)$")
    public void leader_convert_step(Class cls) throws Exception {
        Constructor<LeaderConverter> ctor = cls.getDeclaredConstructor(Model.class, Record.class);
        LeaderConverter converter = ctor.newInstance(model, record);
        model = converter.convert(record.getLeader());
    }

    @When("^I search with patterns:$")
    public void search_triples_step(List<String> triples) throws Exception {
        Set<String> vars = new HashSet<>();
        List<String> list = new ArrayList<>();
        for (String triple: triples) {
            String[] tokens = StringUtils.split(triple.trim(), " ");
            for (String token: tokens) {
                if (token.matches("^\\?.+$")) {
                    vars.add(token);
                }
            }
            if (!triple.trim().endsWith(".")) {
                list.add(triple + " .");
            } else {
                list.add(triple);
            }
        }
        String q = String.format(sparql, StringUtils.join(vars, " "), StringUtils.join(list, "\n"));
        //System.out.println(q);
        query = QueryFactory.create(q);

    }

    @Then("^I should find matches$")
    public void check_result_step() throws Exception {
        model.write(System.out);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();
        assertTrue(results.hasNext());
    }

    @Then("^I should find (\\d+) (?:matches|match)$")
    public void check_result_step(int n) throws Exception {
        //System.out.println(n);
        model.write(System.out);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();
        int i = 0;
        while (results.hasNext()) {
            i++;
            results.next();
        }
        assertEquals(n, i);
    }
}
