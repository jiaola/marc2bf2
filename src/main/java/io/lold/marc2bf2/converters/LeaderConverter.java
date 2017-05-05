package io.lold.marc2bf2.converters;

import io.lold.marc2bf2.mappers.LeaderMapper;
import io.lold.marc2bf2.mappers.Mapper;
import io.lold.marc2bf2.mappings.MappingsReader;
import io.lold.marc2bf2.utils.ModelUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.marc4j.marc.Leader;
import org.marc4j.marc.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LeaderConverter {
    protected Model model;
    protected Record record;
    protected Map<String, Map> mappings;
    protected Map<String, String> prefixMap;
    final static Logger logger = LoggerFactory.getLogger(LeaderConverter.class);

    public LeaderConverter(Model model, Record record) throws Exception {

        this.model = model;
        this.record = record;
        try {
            mappings = (Map<String, Map>) MappingsReader.readMappings("leader");
        } catch (IOException ex) {
            logger.error("Could not load mappings file for leader");
            throw ex;
        }
    }
    public Model convert(Leader leader) throws Exception {
        model = convertToAdminMetadata(leader);

        Resource work = ModelUtils.getWork(model, record);
        model = addTypes(leader, work, "Work");

        Resource instance = ModelUtils.getInstance(model, record);
        model = addTypes(leader, instance, "Instance");

        model = convertToInstance(leader);
        return model;
    }

    private Model convertToAdminMetadata(Leader leader) throws Exception {
        Resource am = ModelUtils.getAdminMatadata(model, record);
        String data = leader.marshal();
        List<Map> positions = (List<Map>) mappings.get("positions");
        for (Map position: positions) {
            if (!position.get("scope").equals("AdminMetadata")) {
                continue;
            }
            int pos = (int) position.get("position"); // the position of the character
            String value = data.substring(pos, pos+1);

            Mapper mapper = new LeaderMapper(model);
            List<RDFNode> nodes = (List<RDFNode>) mapper.map(value, position);
            for (RDFNode node : nodes) {
                am.addProperty(ModelUtils.getProperty((String) position.get("property"), model), node);
            }
        }
        return model;
    }

    public Model convertToInstance(Leader leader) throws Exception {
        Resource instance = ModelUtils.getInstance(model, record);
        String data = leader.marshal();
        List<Map> positions = (List<Map>) mappings.get("positions");
        for (Map position: positions) {
            if (!position.get("scope").equals("Instance")) {
                continue;
            }
            int pos = (int) position.get("position"); // the position of the character
            String value = data.substring(pos, pos+1);

            Mapper mapper = new LeaderMapper(model);
            List<RDFNode> nodes = (List<RDFNode>) mapper.map(value, position);
            for (RDFNode node : nodes) {
                instance.addProperty(ModelUtils.getProperty((String) position.get("property"), model), node);
            }
        }
        return model;
    }

    private Model addTypes(Leader leader, Resource resource, String type) throws Exception {
        String data = leader.marshal();
        List<Map> positions = (List<Map>) mappings.get(type);
        for (Map position: positions) {
            int pos = (int) position.get("position"); // the position of the character
            String value = data.substring(pos, pos+1);
            Map<String, String> uris = (Map<String, String>) position.get("uris");
            if (uris.containsKey(value)) {
                String uri = ModelUtils.getUriWithNsPrefix((String) position.get("prefix"), uris.get(value));
                resource.addProperty(RDF.type, model.createResource(uri));
            }
        }
        return model;
    }
}
