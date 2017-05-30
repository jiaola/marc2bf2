package io.lold.marc2bf2;

import org.apache.commons.cli.*;
import org.marc4j.*;
import org.marc4j.marc.Record;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class RecordTool {
    public static void main(String[] args) throws Exception {
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

        String cn = cmd.getOptionValue('c').trim();
        System.out.println("Looking for " + cn);
        FileInputStream in = new FileInputStream(input);
        FileOutputStream out = new FileOutputStream(output);
        MarcReader reader = new MarcStreamReader(in);
        MarcWriter writer = new MarcXmlWriter(out, "UTF-8", true);

        while (reader.hasNext()) {
            Record record = reader.next();
            if (cn.equals(record.getControlNumber())) {
                System.out.println("Found record");
                System.out.println(record.getControlNumber());
                System.out.println(record.getLeader().marshal());
                System.out.println(record.getLeader().marshal().length());
                writer.write(record);
            } else {
                if (record.getLeader().marshal().length() != 24) {
                    System.out.println(record.getControlNumber());
                }
            }

        }
        //out.close();
        //in.close();

        writer.close();
    }

    private static Options buildOptions() {
        Options options = new Options();
        Option file = Option.builder("i").required(true).longOpt("input").desc("MARC file").hasArg().build();
        Option format = Option.builder("c").required(false).longOpt("controlnumber").desc("Control Number").hasArg().build();
        Option out = Option.builder("o").required(true).longOpt("output").desc("Output RDF file").hasArg().build();
        options.addOption(file).addOption(format).addOption(out);

        return options;
    }
}
