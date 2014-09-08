import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.CycleStrategy;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.strategy.Visitor;
import org.simpleframework.xml.strategy.VisitorStrategy;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputNode;

public class SimpleXmlTest {

	@Test
	public void testName() throws Exception {
		List<String> list = Arrays.asList(new String[]{"a","b","c"});
		LinkedList<String> linkedList = new LinkedList<>(list);
		Example example = new Example("Example message", 123,linkedList);
		List<Foo> foos = new LinkedList<Foo>();
		foos.add(new Foo("1"));
		foos.add(new Foo("2"));
		foos.add(new Foo("3"));
		example.setFoos(foos);
		File result = new File("example.xml");


		Strategy strategy = new VisitorStrategy(new Visitor() {
			
			@Override
			public void write(Type type, NodeMap<OutputNode> node) throws Exception {
				if ("annotations".equals(node.getName())){
					node.remove("class");
				}
			}
			
			@Override
			public void read(Type type, NodeMap<InputNode> node) throws Exception {
				
			}
		});
		Serializer serializer = new Persister(strategy);
		serializer.write(example, result);
	}

}
