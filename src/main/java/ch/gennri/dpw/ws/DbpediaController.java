package ch.gennri.dpw.ws;

import ch.gennri.dpw.ParseException;
import ch.gennri.dpw.Parser;
import ch.gennri.dpw.xml.OntologyChange;

public class DbpediaController {
	
	public OntologyChange convert(String template) throws ParseException {
		Parser parser = new Parser(template);
		return parser.parse();
	}
}
