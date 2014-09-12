package ch.gennri.dpw.ws;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import ch.gennri.dpw.xml.OntologyChange;

@Path(UrlMappings.dbpedia)
public class Dbpedia {

	@Inject
	MyObject myString;
	
	@GET
	@Produces("text/plain")
	public String getIt() {
		return "foobars";
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public String postOntologyChange(OntologyChange xml){
		return myString.value;
//		return xml.getClasses().get(0).getIRI();
	}
	
}
