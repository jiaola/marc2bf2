package io.lold.marc2bf2;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.marc4j.*;
import org.marc4j.marc.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class App {
    final static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) throws Exception {
        Options options = buildOptions();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String input = cmd.getOptionValue('i');
        System.out.println(input);
        if (!new java.io.File(input).isFile()) {
            System.err.println("File not found: " + input);
            System.exit(1);
        }

        String output = cmd.getOptionValue('o');
        if (!new java.io.File(output).isFile()) {
            System.err.println("File not found: " + output);
            System.exit(1);
        }

        String format = "marc";
        if (cmd.hasOption('f')) {
            format = cmd.getOptionValue('f').toLowerCase();
        }
        FileInputStream in = new FileInputStream(new java.io.File(input));
        FileOutputStream out = new FileOutputStream(new java.io.File(output));
        MarcReader reader = null;
        if ("marc".equals(format)) {
            reader = new MarcStreamReader(in);
        } else if ("marcxml".equals(format) || "xml".equals(format)) {
            reader = new MarcXmlReader(in);
        } else {
            System.err.println("File format not supported. Please make sure the format is either marc or marcxml");
            System.exit(1);
        }

        Marc2BibFrame2Converter converter = new Marc2BibFrame2Converter();

        int nRecords = 0, nConverted = 0;
        MarcWriter writer = new MarcXmlWriter(System.out, true);
        while (reader.hasNext()) {
            Record record = reader.next();
            nRecords++;
            if (StringUtils.isBlank(record.getControlNumber())) {
                //writer.write(record);
            } else {
                Model model = ModelFactory.createBfModel();
                model = converter.convert(record, model);
                RDFDataMgr.write(out, model, RDFFormat.NTRIPLES);
                nConverted++;
            }
            if (nRecords % 10000 == 0) {
                logger.info("Processed " + nRecords + " records. Converted " + nConverted + " records.");
            }
        }
        //model.write(out);

        in.close();
        out.close();
    }

    private static Options buildOptions() {
        Options options = new Options();
        Option file = Option.builder("i").required(true).longOpt("input").desc("MARC file").hasArg().build();
        Option format = Option.builder("f").required(false).longOpt("format").desc("File format: marc, marcxml").hasArg().build();
        Option out = Option.builder("o").required(true).longOpt("output").desc("Output RDF file").hasArg().build();
        options.addOption(file).addOption(format).addOption(out);

        return options;
    }
}
