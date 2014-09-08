package ch.gennri.dpw.xml;

import java.io.InputStream;
import java.io.OutputStream;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.strategy.Visitor;
import org.simpleframework.xml.strategy.VisitorStrategy;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputNode;

public class OntologyChangePersister {

	private Persister serializer;
	private Format format;
	private Strategy strategy;
	
	public OntologyChangePersister() {
		this.strategy = new VisitorStrategy(new OntologyChangeVisitor());
		this.format = new Format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
		this.serializer = new Persister(strategy, format);
	}
	
	public void write(Object source, OutputStream out) throws Exception {
		serializer.write(source, out);
	}
	
	public <T> T read(java.lang.Class<? extends T> type, InputStream source) throws Exception {
		return serializer.read(type, source);
	}
	
	private class OntologyChangeVisitor implements Visitor {
			
		@Override
		public void write(Type type, NodeMap<OutputNode> node) throws Exception {
			if (java.util.List.class.equals(type.getType())) {
				node.remove("class");
			}
		}

		@Override
		public void read(Type type, NodeMap<InputNode> node) throws Exception {
			// TODO Auto-generated method stub
		}
	}

}
