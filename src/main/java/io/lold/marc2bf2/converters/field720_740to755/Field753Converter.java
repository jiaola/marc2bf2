package io.lold.marc2bf2.converters.field720_740to755;

import io.lold.marc2bf2.converters.NameTitleFieldConverter;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.VariableField;

import java.util.List;

public class Field753Converter extends NameTitleFieldConverter {
    public Field753Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) throws Exception {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);
        List<Subfield> subfields = df.getSubfields();
        for (int i = 0; i < subfields.size(); i++) {
            Subfield sf = subfields.get(i);
            if (sf.getCode() == 'a' || sf.getCode() == 'b' || sf.getCode() == 'c') {
                Resource resource = model.createResource()
                        .addProperty(RDF.type, getFileType(sf.getCode()))
                        .addProperty(RDFS.label, sf.getData());
                if (i < subfields.size()-1 && subfields.get(i+1).getCode() == '0') {
                    addSubfield0(subfields.get(i+1), resource);
                }
                addSubfield2(df, resource);
                instance.addProperty(BIB_FRAME.systemRequirement, resource);
            }
        }
        return model;
    }

    private Resource getFileType(char code) {
        if (code == 'a') return BIB_FRAME_LC.MachineModel;
        else if (code == 'b') return BIB_FRAME_LC.ProgrammingLanguage;
        else if (code == 'c') return BIB_FRAME_LC.OperatingSystem;
        else return null;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "753".equals(field.getTag());
    }

}
