package ch.gennri.dpw.ws;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.jetty.JettyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DbpediaTest extends JerseyTest {

	public static String dbpediaFile;
	public static String dbpediaSaveFile;
	public static String dbpediaDeleteFile;
	
	private DbpediaController dc;
	private WebProtegeController wc;
	
	@BeforeClass
	public static void staticSetUp() throws IOException {
		
		dbpediaFile = FileUtils.readFileToString(new File("src/test/resources/dbpedia_label.txt"));
//		dbpediaFile = FileUtils.readFileToString(new File("src/test/resources/dbpedia_save.txt"));
//		dbpediaFile = FileUtils.readFileToString(new File("src/test/resources/dbpedia_delete.txt"));
	}
	
	@Override
	protected Application configure() {
		dc = mock(DbpediaController.class);
		wc = mock(WebProtegeController.class);
		enable(TestProperties.LOG_TRAFFIC);
		ResourceConfig resourceConfig = new ResourceConfig(Dbpedia.class);
		resourceConfig.register(OntologyChangeProvider.class);
		resourceConfig.register(new MyHK2Binder(wc, dc));
		return resourceConfig;
	}

	@Override
	protected TestContainerFactory getTestContainerFactory() {
		return new JettyTestContainerFactory();
	}

	@Test
	public void testPost() throws IOException {
		WebTarget t = target("dbpedia");
		
		Response response = t.request().post(Entity.entity(dbpediaFile, MediaType.APPLICATION_XML));
		assertEquals(200, response.getStatus());
		assertEquals("heyho", response.readEntity(String.class));
	}
	
	@Test
	public void dbpediaSavePost() {
		WebTarget t = target("dbpedia/save");
		Response response = t.request().post(Entity.entity(dbpediaFile, MediaType.TEXT_PLAIN));
		assertEquals(200, response.getStatus());
	}
	
	@Test
	public void dbpediaDeletePost() {
		WebTarget t = target("dbpedia/delete");
		String template = "lalala";
		Response response = t.request().post(Entity.entity(template, MediaType.TEXT_PLAIN));
		
		assertEquals(200, response.getStatus());
		verify(dc).convert(template);
	}
}
