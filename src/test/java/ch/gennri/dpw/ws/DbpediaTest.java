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
import org.junit.Test;

public class DbpediaTest extends JerseyTest {

	public static class MyHK2Binder extends AbstractBinder {
		@Override
		protected void configure() {
		// request scope binding
//		bindAsContract(MyObject.class).in(RequestScoped.class);
		// singleton binding
//		bindAsContract(MyInjectableSingleton.class).in(Singleton.class);
//		// singleton instance binding
		bind(new MyObject()).to(MyObject.class);
//		// request scope binding with specified custom annotation
//		bindAsContract(MyInjectablePerRequest.class).qualifiedBy(new MyQualifierImpl()).in(RequestScoped.class);
		}
	}
	
	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
	
		ResourceConfig resourceConfig = new ResourceConfig(Dbpedia.class);
		resourceConfig.register(OntologyChangeProvider.class);
		resourceConfig.register(new MyHK2Binder());
		return resourceConfig;
	}

	@Override
	protected TestContainerFactory getTestContainerFactory() {
		return new JettyTestContainerFactory();
	}

	@Test
	public void testGet() {
		WebTarget t = target("dbpedia");

		String s = t.request().get(String.class);
		Assert.assertEquals("foobars", s);
	}
	
	@Test
	public void testPost() throws IOException {
		String dbpediaFile = FileUtils.readFileToString(new File("src/test/resources/dbpedia_label.txt"));
		WebTarget t = target("dbpedia");
		
		Response post = t.request().post(Entity.entity(dbpediaFile, MediaType.APPLICATION_XML));
		assertEquals(200, post.getStatus());
		assertEquals("heyho", post.readEntity(String.class));
	}
}
