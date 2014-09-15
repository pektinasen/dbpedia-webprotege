package ch.gennri.dpw.ws;

import org.glassfish.jersey.server.ResourceConfig;

public class MyApplication extends ResourceConfig {
	public MyApplication() {
		System.out.println("foobar");
		register(new MyHK2Binder(new WebProtegeController(), new DbpediaController()));
	}
}
