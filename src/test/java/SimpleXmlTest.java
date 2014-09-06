import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class SimpleXmlTest {

	@Test
	public void testName() throws Exception {
		Serializer serializer = new Persister();
		List<String> list = Arrays.asList(new String[]{"a","b","c"});
		Example example = new Example("Example message", 123,list);
		List<Foo> foos = new LinkedList<Foo>();
		foos.add(new Foo("1"));
		foos.add(new Foo("2"));
		foos.add(new Foo("3"));
		example.setFoos(foos);
		File result = new File("example.xml");

		serializer.write(example, result);
	}

}
