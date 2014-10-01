package ch.gennri.dpw.ws;

import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.jetty.JettyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;


public class WebProtegeTest extends JerseyTest {
	
	@Override
	protected Application configure() {
		DbpediaController dc = mock(DbpediaController.class);
		WebProtegeController wc = mock(WebProtegeController.class);
		enable(TestProperties.LOG_TRAFFIC);
		ResourceConfig resourceConfig = new ResourceConfig(WebProtege.class);
		resourceConfig.register(OntologyChangeProvider.class);
		resourceConfig.register(new MyHK2Binder(wc, dc));
		return resourceConfig;
	}

	@Override
	protected TestContainerFactory getTestContainerFactory() {
		return new JettyTestContainerFactory();
	}
	@Test
	public void postTest() throws IOException{
		String ontologyChangeFile = FileUtils.readFileToString(new File("src/test/resources/ontology_change_1.xml"));
		
		WebTarget t = target("webprotege");
		Response response = t.request().post(Entity.entity(ontologyChangeFile, MediaType.APPLICATION_XML));
	}

}
