package ch.gennri.dpw.ws;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.gennri.dpw.ParseException;
import ch.gennri.dpw.Parser;
import ch.gennri.dpw.xml.OntologyChange;
import ch.gennri.dpw.xml.OntologyChangePersister;

public class DbpediaController {
	
	public OntologyChange convert(String template) throws ParseException {
		Parser parser = new Parser(template);
		return parser.parse();
	}
	
	public Response sendToWebProtege(OntologyChange oc, String url) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		OntologyChangePersister persister = new OntologyChangePersister();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			persister.write(oc, stream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ocXml = new String(stream.toByteArray());
		Response response = target.request().post(
				Entity.entity(ocXml, MediaType.APPLICATION_XML), Response.class);
		return response;
	}
}
