package ch.gennri.dpw.ws;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.gennri.dpw.ParseException;
import ch.gennri.dpw.xml.OntologyChange;

@Path(UrlMappings.dbpedia)
public class Dbpedia {

	@Context
	ServletContext servletContext;
	@Inject
	DbpediaController controller;
		
	@POST
	@Path("save")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response postTemplateSave(String template,
			@PathParam(value = "user") String user,
			@PathParam(value = "action") String action){
		OntologyChange oc;
		try {
			oc = controller.convert(template);
		} catch (ParseException e) {
			return Response.status(400).entity(e).build();
		}
		String url = servletContext.getInitParameter("webprotegeurl");
		Response response = controller.sendToWebProtege(oc, url);
		return response;
	}
	
	@POST
	@Path("delete")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response postTemplateDelete(String template,
			@PathParam(value = "user") String user,
			@PathParam(value = "action") String action){
		OntologyChange oc;
		try {
			oc = controller.convert(template);
		} catch (ParseException e) {
			return Response.status(400).entity(e).build();
		}
		String url = servletContext.getInitParameter("webprotegeurl");
		Response response = controller.sendToWebProtege(oc, url);
		return response;
	}
	
}
