package io.lold.marc2bf2;

import org.apache.commons.lang3.StringUtils;
import org.marc4j.MarcException;
import org.marc4j.MarcReader;
import org.marc4j.Mrk8StreamWriter;
import org.marc4j.converter.CharConverter;
import org.marc4j.marc.*;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

public class MrkStreamReader implements MarcReader {
    private final Scanner input;
    private final MarcFactory factory;
    private String lastLineRead;

    public MrkStreamReader() {
        super();
        this.input = null;
        this.factory = MarcFactory.newInstance();
    }

    public MrkStreamReader(InputStream input) {
        this.input = new Scanner(new BufferedInputStream(input), StandardCharsets.UTF_8.name());
        this.factory = MarcFactory.newInstance();
    }

    public boolean hasNext() {
        return this.input.hasNextLine();
    }

    public Record next() {
        ArrayList lines = new ArrayList();
        if(!this.hasNext()) {
            return null;
        } else {
            if(this.lastLineRead != null && this.lastLineRead.substring(1, 4).equalsIgnoreCase("LDR")) {
                lines.add(this.lastLineRead);
                this.lastLineRead = null;
            }

            boolean hasHiBitCharacters = false;

            while(this.input.hasNextLine()) {
                String line = this.input.nextLine();
                if(line.trim().length() != 0) {
                    if(line.substring(1, 4).equalsIgnoreCase("LDR") && lines.size() > 0) {
                        this.lastLineRead = line;
                        break;
                    }

                    lines.add(line);
                }
            }

            return this.parse(lines);
        }
    }

    protected Record parse(List<String> lines) {
        if(lines != null && !lines.isEmpty()) {
            Record record = this.factory.newRecord();
            Iterator var4 = lines.iterator();

            while(true) {
                while(true) {
                    String line;
                    do {
                        if(!var4.hasNext()) {
                            return record;
                        }
                        line = (String)var4.next();
                    } while(line.trim().length() == 0);

                    String tag = line.substring(1, 4);
                    if(!tag.equalsIgnoreCase("LDR")) {
                        Object field;
                        if(this.isControlField(tag)) {
                            field = parseLineToControlField(line);
                        } else {
                            field = parseLineToDataField(line);
                        }
                        record.addVariableField((VariableField)field);
                    } else {
                        record.setLeader(this.getLeader(line.substring(6)));
                    }
                }
            }
        } else {
            return null;
        }
    }

    public VariableField parseLineToVariableField(String line) {
        String tag = StringUtils.substring(line, 1, 4);
        if (tag.equalsIgnoreCase("LDR")) {
            return null;
        }
        if (isControlField(tag)) {
            return parseLineToControlField(line);
        } else {
            return parseLineToDataField(line);
        }
    }

    public ControlField parseLineToControlField(String line) {
        String tag = line.substring(1, 4);
        return this.factory.newControlField(tag, this.unescapeFieldValue(line.substring(6)));
    }

    public DataField parseLineToDataField(String line) {
        String tag = line.substring(1, 4);

        String data = line.substring(6);
        char indicator1 = data.charAt(0) == 92?32:data.charAt(0);
        char indicator2 = data.charAt(1) == 92?32:data.charAt(1);
        if(!this.isValidIndicator(indicator1) || !this.isValidIndicator(indicator2)) {
            throw new MarcException("Wrong indicator format. It has to be a number or a space");
        }

        DataField field = this.factory.newDataField(tag, indicator1, indicator2);
        List subs = Arrays.asList(data.substring(3).split("\\$"));
        Iterator var12 = subs.iterator();

        while(var12.hasNext()) {
            String sub = (String) var12.next();
            String subData = sub.substring(1);

            Subfield subfield = this.factory.newSubfield(sub.charAt(0), subData);
            field.addSubfield(subfield);
        }
        return field;
    }

    public Leader parseLineToLeader(String line) {
        return this.getLeader(line.substring(7));
    }

    protected boolean isValidIndicator(char indicator) {
        return indicator == 32 || indicator >= 48 && indicator <= 57;
    }

    protected Leader getLeader(String substring) {
        Leader leader = this.factory.newLeader();
        leader.unmarshal(substring);
        return leader;
    }

    protected String unescapeFieldValue(String fieldValue) {
        StringBuilder sb = new StringBuilder();
        char[] var3 = fieldValue.toCharArray();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            char c = var3[var5];
            if(c == 92) {
                sb.append(' ');
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    protected boolean isControlField(String tag) {
        return tag.length() == 3 && tag.startsWith("00") && tag.charAt(2) >= 48 && tag.charAt(2) <= 57;
    }
}

