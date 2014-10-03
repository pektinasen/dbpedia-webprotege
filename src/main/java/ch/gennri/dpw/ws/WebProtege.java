package ch.gennri.dpw.ws;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import ch.gennri.dpw.xml.Class;
import ch.gennri.dpw.xml.OntologyChange;
import ch.gennri.dpw.xml.Property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(UrlMappings.webprotege)
public class WebProtege {

    private static Logger logger = LoggerFactory.getLogger(WebProtege.class);

	@Inject
	WebProtegeController controller;

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response postOntologyChange(@CookieParam("token") String token,
                                       @CookieParam("session_name") String session_name,
                                       @CookieParam("session_id") String session_id,
            OntologyChange oc){

		for (Class clazz : oc.getClasses()) {
			String classTemplate = controller.convert(clazz);
            logger.debug("ONTOLOGYCLASS: " + clazz);
			String className = clazz.getIRI();
            String[] splits = className.split("/");
            String article_name = splits[splits.length-1];
            controller.sendToDbpediawithCredentials(classTemplate, "OntologyClass:" + article_name, token, session_name, session_id);
		}
		for (Property p : oc.getDataProperties()) {
			String propertyTemplate = controller.convert(p, "DatatypeProperty");
			String propertyName = p.getIRI();
            logger.debug("DATATYPE_PROPERTY: " + propertyName);
            String[] splits = propertyName.split("/");
            String article_name = splits[splits.length-1];
            controller.sendToDbpediawithCredentials(propertyTemplate, "Datatype:" + article_name, token, session_name, session_id);
		}
		for (Property p : oc.getObjectProperties()) {
			String propertyTemplate = controller.convert(p, "ObjectProperty");
			String propertyName = p.getIRI();
            logger.debug("ONTOLOGY_PROPERTY: " + propertyName);
            String[] splits = propertyName.split("/");
            String article_name = splits[splits.length-1];
            controller.sendToDbpediawithCredentials(propertyTemplate, "OntologyProperty:" + article_name, token, session_name, session_id);
		}
		return Response.ok().build();
	}

}
