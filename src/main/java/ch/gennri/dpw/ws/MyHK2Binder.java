package ch.gennri.dpw.ws;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class MyHK2Binder extends AbstractBinder {

	private WebProtegeController webProtegeController;
	private DbpediaController dbpediaController;
	
	public MyHK2Binder(WebProtegeController webProtegeController, DbpediaController dbpediaController) {
		super();
		this.webProtegeController = webProtegeController;
		this.dbpediaController = dbpediaController;
	}
	
	@Override
	protected void configure() {
		// request scope binding
		// bindAsContract(MyObject.class).in(RequestScoped.class);
		
//		singleton binding
//		bindAsContract(WebProtegeController.class).in(Singleton.class);
//		bindAsContract(DbpediaController.class).in(Singleton.class);
		
		// singleton instance binding
		bind(new MyObject()).to(MyObject.class);
		
		bind(webProtegeController).to(WebProtegeController.class);
		bind(dbpediaController).to(DbpediaController.class);
		
		// request scope binding with specified custom annotation
		// bindAsContract(MyInjectablePerRequest.class).qualifiedBy(new MyQualifierImpl()).in(RequestScoped.class);
		
	}
	
	
	
	

}
