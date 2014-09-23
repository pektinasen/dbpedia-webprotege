package ch.gennri.dpw.ws;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import ch.gennri.dpw.xml.OntologyChange;

public class WebProtegeControllerTest {

	@Test
	public void convertClassSuccess() throws Exception {
		Serializer serializer = new Persister();
		String dbpediaLabel = "src/test/resources/dbpedia_label";
		OntologyChange read = serializer.read(OntologyChange.class, new File(dbpediaLabel +  ".xml"));
		WebProtegeController controller = new WebProtegeController();
		String convert = controller.convert(read.getClasses().get(0));
		String dbpediaString = FileUtils.readFileToString(new File(dbpediaLabel + ".txt"));
		assertEquals(dbpediaString, convert);
	}
}
