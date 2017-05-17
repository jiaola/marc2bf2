package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.utils.FormatUtils;
import io.lold.marc2bf2.utils.ModelUtils;
import io.lold.marc2bf2.utils.RecordUtils;
import io.lold.marc2bf2.vocabulary.BIB_FRAME;
import io.lold.marc2bf2.vocabulary.BIB_FRAME_LC;
import io.lold.marc2bf2.vocabulary.MADS_RDF;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Process X00, X10, X11 (X=1, 6, 7, 8)
 */
public abstract class NameTitleFieldConverter extends FieldConverter {
    final static Logger logger = LoggerFactory.getLogger(NameTitleFieldConverter.class);

    public NameTitleFieldConverter(Model model, Record record) {
        super(model, record);
    }

    /**
     * Build a work for field 6XX, ??? TODO: update this
     *
     * @param field
     * @return
     */
    protected Resource buildWork(DataField field) {
        String workUri = ModelUtils.buildUri(record, "Work", field.getTag(), fieldIndex);

        Resource resource = model.createResource(workUri)
                .addProperty(RDF.type, BIB_FRAME.Work);
        String label = nameLabel(field).trim() + " " + titleLabel(field).trim();
        addMads(field, resource, label);

        Resource source = buildSource(field);
        if (source != null) {
            resource.addProperty(BIB_FRAME.source, source);
        }
        String tag = getTag(field);
        String lang = RecordUtils.getXmlLang(field, record);
        List<Subfield> sfs = "11".equals(StringUtils.substring(tag, 1, 3)) ?
                field.getSubfields('j') :
                field.getSubfields('e');
        Resource work = ModelUtils.getWork(model, record);
        List<Resource> relationships = contributionRelationship(sfs, lang, work);
        for (Resource relationship: relationships) {
            resource.addProperty(BIB_FRAME_LC.relationship, relationship);
        }
        for (Subfield sf4: field.getSubfields('4')) {
            String relator = StringUtils.substring(sf4.getData(), 0, 3);
            resource.addProperty(BIB_FRAME_LC.relationship, createRelationship(relator, work));
        }
        resource.addProperty(BIB_FRAME.contribution, buildContribution(field));
        addUniformTitle(field, resource);
        return resource;
    }

    /**
     * Build bf:Source based on indicator 2. Only for 600, 610, 611
     *
     * @param field
     * @return
     */
    protected Resource buildSource(DataField field) {
        String code = ModelUtils.getSubjectThesaurusCode(field.getIndicator2());
        Resource source = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Source);
        if (StringUtils.isBlank(code) && field.getIndicator2() == '7') {
            return source.addProperty(BIB_FRAME.code, field.getSubfieldsAsString("2"));
        } else if (StringUtils.isNotBlank(code)) {
            return source.addProperty(BIB_FRAME.code, code);
        } else {
            return null;
        }
    }

    protected void addUniformTitle(DataField field, Resource resource) {
        String tag = getTag(field);
        String lang = RecordUtils.getXmlLang(field, record);
        String label = titleLabel(field);
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(RDFS.label, createLiteral(label, lang));
        }

        resource.addProperty(BIB_FRAME.title, buildUniformTitle(field, label, tag, lang));

        String tagcode = StringUtils.substring(tag, 1, 3);
        List<Subfield> sfs;
        if ("10".equals(tagcode)) {
            sfs = RecordUtils.lookAhead(field, 't', "d");
        } else if ("30".equals(tagcode) || "40".equals(tagcode)) {
            sfs = field.getSubfields('d');
        } else {
            sfs = new ArrayList<>();
        }
        for (Subfield sf: sfs) {
            String value = FormatUtils.chopPunctuation(FormatUtils.chopParens(FormatUtils.chopPunctuation(sf.getData())));
            resource.addProperty(BIB_FRAME.legalDate, createLiteral(value, lang));
        }

        for (Subfield sf: field.getSubfields('f')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.originDate, createLiteral(value, lang));
        }

        if ("30".equals(tagcode) || "40".equals(tagcode)) {
            sfs = field.getSubfields('g');
        } else {
            sfs = RecordUtils.lookAhead(field, 't', "g");
        }
        for (Subfield sf: sfs) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.genreForm, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.GenreForm)
                    .addProperty(RDFS.label, createLiteral(value, lang)));
        }
        for (Subfield sf: field.getSubfields('h')) {
            String value = FormatUtils.chopPunctuation(FormatUtils.chopBrackets(FormatUtils.chopParens(sf.getData())));
            resource.addProperty(BIB_FRAME.genreForm, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.GenreForm)
                    .addProperty(RDFS.label, createLiteral(value, lang)));
        }
        for (Subfield sf: field.getSubfields('k')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.natureOfContent, createLiteral(value, lang))
                    .addProperty(BIB_FRAME.genreForm, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.GenreForm)
                    .addProperty(RDFS.label, createLiteral(value, lang)));
        }
        for (Subfield sf: field.getSubfields('l')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.language, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.Language)
                    .addProperty(RDFS.label, createLiteral(value, lang)));
        }
        for (Subfield sf: field.getSubfields('m')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.musicMedium, model.createResource()
                    .addProperty(RDF.type, BIB_FRAME.MusicMedium)
                    .addProperty(RDFS.label, createLiteral(value, lang)));
        }
        for (Subfield sf: field.getSubfields("os")) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.version, createLiteral(value, lang));
        }
        for (Subfield sf: field.getSubfields('r')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.musicKey, createLiteral(value, lang));
        }
        if (tag.startsWith("7") || tag.startsWith("8")) {
            for (Subfield sf: field.getSubfields('x')) {
                resource.addProperty(BIB_FRAME.identifiedBy, model.createResource()
                        .addProperty(RDF.type, BIB_FRAME.Issn)
                        .addProperty(RDF.value, sf.getData()));
            }
        }
        if ("30".equals(tagcode) || "240".equals(tag) || field.getSubfield('t') != null) {
            addSubfield0(field, resource);
            addSubfield3(field, resource);
            addSubfield5(field, resource);
        }
        addSubfieldW(field, resource);
        if ("830".equals(tag)) {
            addSubfield7(field, resource);
        }
    }

    protected Resource buildUniformTitle(DataField field, String label, String tag, String lang) {
        int nfi;
        if ("130".equals(tag) || "630".equals(tag) || "730".equals(tag) || "740".equals(tag)) {
            nfi = Character.getNumericValue(field.getIndicator1());
        } else if ("240".equals(tag) || "830".equals(tag) || "440".equals(tag)) {
            nfi = Character.getNumericValue(field.getIndicator2());
        } else {
            nfi = 0;
        }

        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Title);
        addTitleMatchMarcKey(field, resource, label);
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(RDFS.label, label);
            resource.addProperty(BIB_FRAME_LC.titleSortKey, StringUtils.substring(label, nfi));
        }

        String tagcode = StringUtils.substring(tag, 1, 3);
        char sfcode = "30".equals(tagcode) || "40".equals(tagcode) ? 'a' : 't';
        for (Subfield sf: field.getSubfields(sfcode)) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.mainTitle, createLiteral(value, lang));
        }
        List<Subfield> sfs = "11".equals(tagcode) ?
                RecordUtils.lookAhead(field, 't', "n") :
                field.getSubfields('n');
        for (Subfield sf: sfs) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.partNumber, createLiteral(value, lang));
        }

        for (Subfield sf: field.getSubfields('p')) {
            String value = FormatUtils.chopPunctuation(sf.getData());
            resource.addProperty(BIB_FRAME.partName, createLiteral(value, lang));
        }
        return resource;
    }

    /**
     * Builds a bf:Contribution node
     *
     * @param field
     * @return
     */
    protected Resource buildContribution(DataField field) {
        String tag = getTag(field);

        Resource resource = model.createResource()
                .addProperty(RDF.type, BIB_FRAME.Contribution);
        if (tag.startsWith("1")) {
            resource.addProperty(RDF.type, BIB_FRAME_LC.PrimaryContribution);
        }

        List<Subfield> sfes = field.getSubfields('e');
        List<Subfield> sfjs = field.getSubfields('j');
        List<Subfield> sf4s = field.getSubfields('4');
        List<Resource> roles = new ArrayList<>();
        String lang = RecordUtils.getXmlLang(field, record);
        if ((tag.endsWith("00") || tag.endsWith("10") || "720".equals(tag)) && !sfes.isEmpty()) {
            roles = contributionRole(sfes, lang);
        } else if (tag.endsWith("11") && !sfjs.isEmpty()) {
            roles = contributionRole(sfjs, lang);
        } else if (!sf4s.isEmpty()) {
            roles = contributionRoleCode(sf4s);
        } else {
            String uri = ModelUtils.getUriWithNsPrefix("relators", "ctb");
            resource.addProperty(BIB_FRAME.role, model.createResource(uri));
        }
        for (Resource role: roles) {
            resource.addProperty(BIB_FRAME.role, role);
        }

        resource.addProperty(BIB_FRAME.agent, buildAgent(field));

        return resource;
    }

    /**
     * Builds a bf:Agent node
     *
     * @param field
     * @return
     */
    protected Resource buildAgent(DataField field) {
        String lang = RecordUtils.getXmlLang(field, record);
        String agentUri = Optional.ofNullable(generateFieldUri(field, BIB_FRAME.Agent))
                .orElse(ModelUtils.buildUri(record, "Agent", field.getTag(), fieldIndex));
        Resource agent = model.createResource(agentUri)
                .addProperty(RDF.type, BIB_FRAME.Agent);
        Resource agentType = getAgentType(field);
        if (agentType != null) {
            agent.addProperty(RDF.type, agentType);
        }

        // bflc:nameXXMarkKey
        // bflc:nameXXMatchKey
        String label = nameLabel(field);
        addNameMatchMarcKey(field, agent, label);
        if (StringUtils.isNotBlank(label)) {
            agent.addProperty(RDFS.label, createLiteral(label, lang));
        }

        // Only 6xx needs mads class (or 880 $6 6xx)
        String tag = getTag(field);
        if (tag.startsWith("6")) {
            addMads(field, agent, label);

            Resource source = buildSource(field);
            if (source != null) {
                agent.addProperty(BIB_FRAME.source, source);
            }

            if (field.getSubfields('t').isEmpty()) {
                List<Subfield> sfs = "11".equals(StringUtils.substring(tag, 1, 3)) ?
                    field.getSubfields('j') :
                    field.getSubfields('e');
                List<Resource> relationships = contributionRelationship(sfs, lang, ModelUtils.getWork(model, record));
                for (Resource relationship: relationships) {
                    agent.addProperty(BIB_FRAME_LC.relationship, relationship);
                }
            }
        }

        if (field.getSubfields('t').isEmpty()) {
            addSubfield0(field, agent);
            addSubfield3(field, agent);
            addSubfield5(field, agent);
        }
        return agent;
    }

    /**
     * Add MADSRDF prefixed nodes
     * @param field
     * @param resource
     * @param label
     */
    protected void addMads(DataField field, Resource resource, String label) {
        String tag = getTag(field);
        String lang = RecordUtils.getXmlLang(field, record);
        Resource madsClass = null;
        if (!field.getSubfields("vxyz").isEmpty()) {
            madsClass = MADS_RDF.ComplexSubject;
        } else if ("630".equals(tag)) {
            madsClass = MADS_RDF.Title;
        } else if (!field.getSubfields('t').isEmpty()) {
            madsClass = MADS_RDF.NameTitle;
        } else if ("600".equals(tag)) {
            madsClass = MADS_RDF.Name;
        } else if ("610".equals(tag)) {
            madsClass = MADS_RDF.CorporateName;
        } else if ("611".equals(tag)) {
            madsClass = MADS_RDF.ConferenceName;
        }
        if (madsClass != null) {
            resource.addProperty(RDF.type, madsClass);
            String madsLabel = "";
            if (StringUtils.isNotBlank(label)) {
                madsLabel += FormatUtils.chopPunctuation(label, ":,;/\\s");
            }
            if (!field.getSubfields("vxyz").isEmpty()) {
                madsLabel += "--";
            }
            madsLabel += concatSubfields(field, "vxyz", "--");
            madsLabel = FormatUtils.chopPunctuation(madsLabel, "-\\s");
            if (StringUtils.isNotBlank(madsLabel)) {
                resource.addProperty(MADS_RDF.authoritativeLabel, createLiteral(madsLabel, lang));
            }
            String[] schemes = ModelUtils.getMADSScheme(field.getIndicator2());
            for (String scheme: schemes) {
                resource.addProperty(MADS_RDF.isMemberofMADSScheme, model.createResource(scheme));
            }
        }
    }

    /**
     * Creates the name label from a field
     *
     * @param field
     * @return
     */
    protected String nameLabel(DataField field) {
        String tag = getTag(field);
        if ("720".equals(tag)) {
            return field.getSubfieldsAsString("a");
        }

        switch (StringUtils.substring(tag, 1,3)) {
            case "00":
                return RecordUtils.getSubfieldsAsString(field, "abcdjq");
            case "10":
                return RecordUtils.getSubfieldsAsStringBefore(field, "abcdng", 't');
            case "11":
                return RecordUtils.getSubfieldsAsStringBefore(field, "acdengq", 't');
            default:
                return "";
        }
    }

    /**
     * Creates a title label from a field
     * @param field
     * @return
     */
    protected String titleLabel(DataField field) {
        String tag = getTag(field);
        switch (StringUtils.substring(tag, 1, 3)) {
            case "00":
                return RecordUtils.getSubfieldsAsStringAfterAnd(field, "tfgklmnoprs", 't');
            case "10":
                return RecordUtils.getSubfieldsAsStringAfterAnd(field, "tdfgklmnoprs", 't');
            case "11":
                return RecordUtils.getSubfieldsAsStringAfterAnd(field, "tfgklnps", 't');
            default:
                return RecordUtils.getSubfieldsAsString(field, "adfgklmnoprs");
        }
    }

    /**
     * Returns the Type of the agent
     * @param field
     * @return
     */
    protected Resource getAgentType(DataField field) {
        if ("720".equals(field.getTag()) && field.getIndicator1() == '1') {
            return BIB_FRAME.Person;
        } else if (field.getTag().endsWith("00")) {
            if (field.getIndicator1() == '3') {
                return BIB_FRAME.Family;
            } else {
                return BIB_FRAME.Person;
            }
        } else if (field.getTag().endsWith("10")) {
            if (field.getIndicator1() == '1') {
                return BIB_FRAME.Jurisdiction;
            } else {
                return BIB_FRAME.Organization;
            }
        } else if (field.getTag().endsWith("11")) {
            return BIB_FRAME.Meeting;
        }
        return null;
    }

    /**
     * Add bflc:nameXXMatchKey and bflc:nameXXMarcKey
     *
     * @param field
     * @param resource
     * @param label
     */
    protected void addNameMatchMarcKey(DataField field, Resource resource, String label) {
        String tag = getTag(field);
        String subcode = StringUtils.substring(tag, 1, 3);
        List<String> tags = Arrays.asList("00", "10", "11");
        if (!tags.contains(subcode)) {
            return;
        }
        Property matchKey = model.createProperty(BIB_FRAME_LC.getURI(), "name" + subcode + "MatchKey");
        Property primary = model.createProperty(BIB_FRAME_LC.getURI(), "primaryContributorName" + subcode + "MatchKey");
        Property marcKey = model.createProperty(BIB_FRAME_LC.getURI(), "name" + subcode + "MarcKey");
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(matchKey, label);
            if (tag.startsWith("1")) {
                resource.addProperty(primary, label);
            }
        }
        resource.addProperty(marcKey, RecordUtils.marcKey(field));
    }

    /**
     * Add bflc:titleXXMatchKey and title:nameXXMarcKey
     *
     * @param field
     * @param resource
     * @param label
     */
    protected void addTitleMatchMarcKey(DataField field, Resource resource, String label) {
        String tag = getTag(field);
        String subcode = StringUtils.substring(tag, 1, 3);
        List<String> tags = Arrays.asList("00", "10", "11", "30", "40");
        if (!tags.contains(subcode) || "740".equals(tag)) {
            return;
        }
        Property matchKey = model.createProperty(BIB_FRAME_LC.getURI(), "title" + subcode + "MatchKey");
        Property marcKey = model.createProperty(BIB_FRAME_LC.getURI(), "title" + subcode + "MarcKey");
        if (StringUtils.isNotBlank(label)) {
            resource.addProperty(matchKey, label);
        }
        resource.addProperty(marcKey, RecordUtils.marcKey(field));
    }

    protected String buildNewWorkUri(DataField field) {
        String workUri = null;
        for (Subfield sf0orw: field.getSubfields("0w")) {
            workUri = getUriFromSubfield0OrW(sf0orw);
            if (StringUtils.isNotBlank(workUri)) {
                break;
            }
        }
        if (StringUtils.isBlank(workUri)) {
            workUri = ModelUtils.buildUri(record, "Work", getTag(field), fieldIndex);
        }
        return workUri;
    }

}
