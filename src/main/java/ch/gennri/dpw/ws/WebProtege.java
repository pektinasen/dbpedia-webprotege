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

@Path(UrlMappings.webprotege)
public class WebProtege {

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
			String className = clazz.getIRI();
            controller.sendToDbpediawithCredentials(classTemplate, className, token, session_name, session_id);
		}
		for (Property p : oc.getDataProperties()) {
			String propertyTemplate = controller.convert(p, "DatatypeProperty");
			String propertyName = p.getIRI();
            controller.sendToDbpediawithCredentials(propertyTemplate, propertyName, token, session_name, session_id);
		}
		for (Property p : oc.getObjectProperties()) {
			String propertyTemplate = controller.convert(p, "ObjectProperty");
			String propertyName = p.getIRI();
            controller.sendToDbpediawithCredentials(propertyTemplate, propertyName, token, session_name, session_id);
		}
		return Response.ok().build();
	}

}
