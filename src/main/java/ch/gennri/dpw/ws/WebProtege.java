package ch.gennri.dpw.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

import ch.gennri.dpw.xml.OntologyChange;

public class WebProtege {

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public String postOntologyChange(OntologyChange xml){
		return xml.getClasses().get(0).getIRI();
	}
	
}