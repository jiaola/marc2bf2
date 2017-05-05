package io.lold.marc2bf2;

import java.io.Closeable;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

import org.marc4j.MarcWriter;
import org.marc4j.converter.CharConverter;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Leader;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

/**
 * Class for writing MARC record objects in MRK8 format.
 * <p>
 * The following example reads a file with MARCXML records and outputs the
 * record set in MRK8 format:
 * </p>
 *
 * <pre>
 * InputStream input = new FileInputStream(&quot;marcxml.xml&quot;);
 * MarcXmlReader reader = new MarcXmlReader(input);
 * MarcWriter writer = new Mrk8StreamWriter(System.out);
 * while (reader.hasNext()) {
 *     Record record = reader.next();
 *     writer.write(record);
 * }
 * writer.close();
 * </pre>
 *
 *
 * @author Binaek Sarkar
 */
public class MrkStreamWriter implements MarcWriter, Closeable {

    private final PrintWriter mrk8Writer;
    private final CharsetEncoder encoder;

    /**
     * Constructs an instance and creates a {@link MarcWriter} object with the
     * specified output stream.
     *
     * @param output The {@link OutputStream} to write to
     */
    public MrkStreamWriter(final OutputStream output) {
        this.encoder = StandardCharsets.UTF_8.newEncoder();
        this.mrk8Writer = new PrintWriter(output);
    }

    /**
     * Writes a {@link Record} object to the given {@link OutputStream}
     *
     * @param record - the <code>Record</code> object
     */
    @Override
    public void write(final Record record) {
        final StringBuilder recordStringBuilder = new StringBuilder();

        final Leader ldr = record.getLeader();
        recordStringBuilder.append("=").append("LDR").append("  ").append(ldr.marshal());

        for (final VariableField field : record.getVariableFields()) {
            recordStringBuilder.append("=").append(field.getTag()).append("  ");

            if (field instanceof ControlField) {
                final ControlField controlField = (ControlField) field;
                String data = controlField.getData();
//                try {
//                    data = this.encoder.encode(CharBuffer.wrap(controlField.getData())).asCharBuffer().toString();
//                } catch (CharacterCodingException cce) {
//                    data = controlField.getData();
//                }
                recordStringBuilder.append(data);
            } else if (field instanceof DataField) {
                final DataField dataField = (DataField) field;
                recordStringBuilder.append((dataField.getIndicator1() == ' ') ? "\\" : dataField.getIndicator1());
                recordStringBuilder.append((dataField.getIndicator2() == ' ') ? "\\" : dataField.getIndicator2());

                for (final Subfield subField : dataField.getSubfields()) {
                    String data = subField.getData();
//                    try {
//                        data = this.encoder.encode(CharBuffer.wrap(subField.getData())).asCharBuffer().toString();
//                    } catch (CharacterCodingException cce) {
//                        data = subField.getData();
//                    }
                    recordStringBuilder.append("$").append(subField.getCode()).append(data);
                }
            }
            recordStringBuilder.append(System.lineSeparator());
        }
        recordStringBuilder.append(System.lineSeparator());

        this.mrk8Writer.append(recordStringBuilder);
        this.mrk8Writer.flush();
    }

    /**
     * This should set the character converter.
     *
     * However, since the {@link MrkStreamWriter} uses the UTF-8 encoder from
     * {@link StandardCharsets} this method has no effect
     *
     * @param converter the character converter
     */
    @Override
    public void setConverter(final CharConverter converter) {
    }

    /**
     * Returns the character converter.
     *
     * In this case, always returns <code>null</code>
     *
     * @return CharConverter always <code>null</code>
     */
    @Override
    public CharConverter getConverter() {
        return null;
    }

    /**
     * Closes the writer and the underlying {@link OutputStream}
     */
    @Override
    public void close() {
        this.mrk8Writer.flush();
        this.mrk8Writer.close();
    }
}