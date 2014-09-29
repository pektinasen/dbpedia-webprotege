package ch.gennri.dpw.ws;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
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
	public Response postOntologyChange(OntologyChange oc){
		
		for (Class clazz : oc.getClasses()) {
			String classTemplate = controller.convert(clazz);
			String className = clazz.getIRI();
			controller.sendToDbpedia(classTemplate, className);
		}
		for (Property p : oc.getDataProperties()) {
			String propertyTemplate = controller.convert(p, "DatatypeProperty");
			String propertyName = p.getIRI();
			controller.sendToDbpedia(propertyTemplate, propertyName);
		}
		for (Property p : oc.getObjectProperties()) {
			String propertyTemplate = controller.convert(p, "ObjectProperty");
			String propertyName = p.getIRI();
			controller.sendToDbpedia(propertyTemplate, propertyName);
		}
		return Response.ok().build();
	}
	
}
