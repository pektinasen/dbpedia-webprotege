package ch.gennri.dpw;

import java.util.List;

import org.junit.Test;

import ch.gennri.dpw.xml.Annotation;
import ch.gennri.dpw.xml.Class;
import ch.gennri.dpw.xml.OntologyChange;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static ch.gennri.dpw.Tokens.*;

public class ParserTest {

	@Test
	public void classOneLabelTemplate() throws ParseException {
		Tokenizer tokenizerMock = mock(Tokenizer.class);
		when(tokenizerMock.next()).thenReturn(
				templateBegin,
				wordClass,
				parameterSeperator,
				rdfsLabelEn,
				equals,
				album,
				parameterSeperator,
				rdfsSubclassOf,
				equals,
				musicalWork,
				parameterSeperator,
				owlToken,
				equals,
				schemaMusicAlbum,
				comma,
				wikidata,
				templateEnd);
		
		Parser parser = new Parser(tokenizerMock);
		OntologyChange oc = parser.parse();
		Class clazz = oc.getClasses().get(0);
		Annotation annotation = clazz.getAnnotations().get(0);
		String subClass = clazz.getSub_classes().get(0);
		List<String> equiClasses = clazz.getEquivalent_classes();
		assertEquals("rdfs:label@en", annotation.getType());
		assertEquals("album", annotation.getAnnotation());
		assertEquals("MusicalWork", subClass);
		assertEquals("schema:MusicAlbum", equiClasses.get(0));
		assertEquals("wikidata:Q482994", equiClasses.get(1));
		
		

	}
}
