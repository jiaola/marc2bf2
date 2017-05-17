package io.lold.marc2bf2.converters.field3XX;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field352Converter extends Field344Converter {
    public Field352Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        String lang = RecordUtils.getXmlLang(df, record);
        List<Subfield> sfs = df.getSubfields();
        for (int i = 0; i < sfs.size(); i++) {
            Subfield sf = sfs.get(i);
            if (sf.getCode() != 'a' && sf.getCode() != 'q' && sf.getCode() != 'b') {
                continue;
            }
            String value = FormatUtils.chopPunctuation(sf.getData());
            Resource resource;
            if (sf.getCode() == 'a') {
                resource = createLabeledResource(BIB_FRAME.CartographicDataType, value, lang);
            } else if (sf.getCode() == 'q') {
                resource = createLabeledResource(BIB_FRAME.EncodingFormat, value, lang);
            } else { // sf.getCode() == 'b'
                resource = createLabeledResource(BIB_FRAME.CartographicObjectType, value, lang);
                if (i < sfs.size() - 1) {
                    Subfield sfc = sfs.get(i+1);
                    if (sfc.getCode() == 'c') {
                        String c = FormatUtils.chopParens(FormatUtils.chopPunctuation(sfc.getData()));
                        resource.addProperty(BIB_FRAME.count, createLiteral(c, lang));
                    }
                }
            }
            instance.addProperty(BIB_FRAME.digitalCharacteristic, resource);
        }

        return model;
    }
    @Override
    public boolean checkField(VariableField field) {
        return "352".equals(field.getTag());
    }
}
