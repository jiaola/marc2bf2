package io.lold.marc2bf2.converters.field841to887;

import com.sun.org.apache.xpath.internal.operations.Mod;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.*;

import java.util.ArrayList;
import java.util.List;

public class Field856Converter extends Field850Converter {
    public Field856Converter(Model model, Record record) {
        super(model, record);
    }

    @Override
    protected Model process(VariableField field) {
        DataField df = (DataField) field;
        Resource instance = ModelUtils.getInstance(model, record);

        // $u is required
        if (df.getSubfields('u') == null) return model;

        char ind2 = df.getIndicator2();
        if (ind2 == '2') {
            for (Resource locator: locators(df)) {
                instance.addProperty(BIB_FRAME.supplementaryContent, locator);
            }
        } else {
            Leader leader = record.getLeader();
            boolean isLeaderElectronic = leader.getTypeOfRecord() == 'm';
            boolean is007Electronic;
            ControlField f007 = (ControlField) record.getVariableField("007");
            is007Electronic = f007 != null && "m".equals(StringUtils.substring(f007.getData(), 0, 1));
            boolean is008Electronic = false;
            ControlField f008 = (ControlField) record.getVariableField("008");
            if (f008 != null) {
                String digit = StringUtils.substring(f008.getData(), 23, 1);
                is008Electronic = "q".equals(digit) || "s".equals(digit);
            }
            if (ind2 == '0' || isLeaderElectronic || is007Electronic || is008Electronic) {
                String itemUri = ModelUtils.buildUri(record, "Item", getTag(df), fieldIndex);
                Resource item = model.createResource(itemUri)
                        .addProperty(RDF.type, BIB_FRAME.Item);
                for (Resource locator: locators(df)) {
                    item.addProperty(BIB_FRAME.electronicLocator, locator);
                }
                instance.addProperty(BIB_FRAME.hasItem, item.addProperty(BIB_FRAME.itemOf, instance));
            } else {
                Resource work = ModelUtils.getWork(model, record);
                String uri = ModelUtils.buildUri(record, "Instance", field.getTag(), fieldIndex);
                Resource resource = model.createResource(uri)
                        .addProperty(RDF.type, BIB_FRAME.Instance)
                        .addProperty(RDF.type, BIB_FRAME.Electronic);
                DataField f245 = (DataField) record.getVariableField("245");
                if (f245 != null) {
                    resource.addProperty(BIB_FRAME.title,
                            buildTitleFrom245(f245, lang, concatSubfields(f245, "abfgknps", " ")));
                }
                String itemUri = ModelUtils.buildUri(record, "Item", field.getTag(), fieldIndex);
                Resource item = model.createResource(itemUri)
                        .addProperty(RDF.type, BIB_FRAME.Item);
                for (Resource locator: locators(df)) {
                    item.addProperty(BIB_FRAME.electronicLocator, locator);
                }
                resource.addProperty(BIB_FRAME.hasItem, item.addProperty(BIB_FRAME.itemOf, resource));
                work.addProperty(BIB_FRAME.hasInstance, resource.addProperty(BIB_FRAME.instanceOf, work));
            }

        }

        return model;
    }

    private List<Resource> locators(DataField field) {
        List<Resource> locators = new ArrayList<>();
        for (Subfield sf : field.getSubfields('u')) {
            List<Subfield> sfzy3s = field.getSubfields("zy3");
            if (sfzy3s.isEmpty()) {
                locators.add(model.createResource(sf.getData()));
            } else {
                Resource locator = model.createResource()
                        .addProperty(BIB_FRAME_LC.locator, model.createResource(sf.getData()));
                for (Subfield sfzy3 : sfzy3s) {
                    locator.addProperty(BIB_FRAME.note, createLabeledResource(BIB_FRAME.Note, sfzy3.getData()));
                }
                locators.add(locator);
            }
        }
        return locators;
    }

    @Override
    public boolean checkField(VariableField field) {
        return "856".equals(field.getTag());
    }

}
