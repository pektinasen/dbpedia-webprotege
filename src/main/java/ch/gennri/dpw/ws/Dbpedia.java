package ch.gennri.dpw.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.gennri.dpw.xml.OntologyChange;

@Path(UrlMappings.dbpedia)
public class Dbpedia {

//	@Context
//	Request request;
//	@Context
//	private UriInfo uriInfo;
	
	
	
//	public Dbpedia(UriInfo uriInfo, Request request) {
//		this.uriInfo = uriInfo;
//		this.request = request;
//	}
	
	@GET
	@Produces("text/plain")
	public String getIt() {
		// TODO Auto-generated method stub
		return "lalalalala";
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public String postOntologyChange(OntologyChange xml){
		return xml.getClasses().get(0).getIRI();
	}
	
}
