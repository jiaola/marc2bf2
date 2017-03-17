package io.lold;

import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VCARD;

import java.util.HashMap;

public class App {
    public static void main( String[] args ) throws Exception {

        // create an empty model
        Model model = io.lold.marc2bf2.ModelFactory.createBfModel();

        String workURI = "http://example.org/13600108#Work";

        Resource local = model.createResource().
                addProperty(RDF.type, BIB_FRAME.Local).
                addProperty(RDF.value, "13600108");

        Resource bf =
                model.createResource(workURI)
                .addProperty(RDF.type, BIB_FRAME.Work)
                .addProperty(BIB_FRAME.identifiedBy, local);

        // now write the model in XML form to a file
        model.write(System.out);
    }
}
