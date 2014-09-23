package ch.gennri.dpw.ws;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;


public class WebProtegeTest extends JerseyTest {
	
	@Override
	protected Application configure() {
		// TODO Auto-generated method stub
		return new ResourceConfig(WebProtege.class);
	}
	
	@Test
	public void postTest(){
		
	}

}
