package ch.gennri.dpw;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.gennri.dpw.xml.Annotation;
import ch.gennri.dpw.xml.Class;
import ch.gennri.dpw.xml.OntologyChange;
import ch.gennri.dpw.xml.Property;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static ch.gennri.dpw.Tokens.*;

public class ParserTest {

	Logger logger = LoggerFactory.getLogger(ParserTest.class);
	
	@Test
	public void classWithRdfsLabel() throws ParseException {
		Tokenizer tokenizerMock = mock(Tokenizer.class);
		when(tokenizerMock.next()).thenReturn(
				templateBegin,
				wordClass,
				symbolParametSep,
				rdfsLabelEn,
				symbolEquals,
				album,
				symbolParametSep,
				rdfsSubclassOf,
				symbolEquals,
				musicalWork,
				symbolParametSep,
				owlEquivalent,
				symbolEquals,
				equivalentClasses,
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
	
	@Test
	public void classWithLabelTemplate() throws Exception {
		Tokenizer tokenizerMock = mock(Tokenizer.class);
		when(tokenizerMock.next()).thenReturn(
			Tokens.templateBegin,
			Tokens.wordObjectproperty,
			Tokens.symbolParametSep,
			Tokens.labels,
			Tokens.symbolEquals,
			Tokens.templateBegin,
			Tokens.label,
			Tokens.symbolParametSep,
			Tokens.nameEn,
			Tokens.symbolParametSep,
			Tokens.nameSchool,
			Tokens.templateEnd,
			Tokens.templateBegin,
			Tokens.label,
			Tokens.symbolParametSep,
			Tokens.nameDe,
			Tokens.symbolParametSep,
			Tokens.nameSchule,
			Tokens.templateEnd,
			Tokens.symbolParametSep,
			Tokens.comments,
			Tokens.symbolEquals,
			Tokens.templateBegin,
			Tokens.comment,
			Tokens.symbolParametSep,
			Tokens.nameEn,
			Tokens.symbolParametSep,
			Tokens.commentSchool1,
			Tokens.commentSchool2,
			Tokens.commentSchool3,
			Tokens.commentSchool4,
			Tokens.commentSchool5,
			Tokens.templateEnd,
			Tokens.templateEnd
		);
		
		Parser parser = new Parser(tokenizerMock);
		OntologyChange oc = parser.parse();
		Property objectProperty = oc.getObjectProperties().get(0);
		List<Annotation> annotations = objectProperty.getAnnotations();
		Annotation annotation0 = annotations.get(0);
		Annotation annotation1 = annotations.get(1);
		Annotation annotation2 = annotations.get(2);
		assertEquals("rdfs:label@en", annotation0.getType());
		assertEquals("School", annotation0.getAnnotation());
		assertEquals("rdfs:label@de", annotation1.getType());
		assertEquals("Schule", annotation1.getAnnotation());
		assertEquals("rdfs:comment@en", annotation2.getType());
		assertEquals("school a person goes to", annotation2.getAnnotation());
	}
	
	@Test
	public void readAnime() throws FileNotFoundException, ParseException {
		Tokenizer tokenizer = new Tokenizer(new FileReader("src/test/resources/dbpedia_anime.txt"));
		Parser parser = new Parser(tokenizer);
		OntologyChange oc = parser.parse();
		
		Class clazz = oc.getClasses().get(0);
		List<Annotation> annotations = clazz.getAnnotations();
		assertEquals("Cartoon", clazz.getSub_classes().get(0));
		for (Annotation a : annotations) {
			logger.debug("{} = {}", a.getType(), a.getAnnotation());
		}
		assertEquals(8, annotations.size());
		System.out.println(oc.getAppendix());
		assertTrue("has Appendix", oc.getAppendix() != null);
		
//		{{Class
//			| rdfs:label@en = anime
//			| rdfs:label@de = anime
//			| rdfs:label@el = άνιμε
//			| rdfs:label@it = anime
//			| rdfs:label@ja = アニメ
//			| rdfs:label@ko = 일본의 애니메이션
//			| rdfs:subClassOf = Cartoon
//			| rdfs:comment@en = A style of animation originating in Japan
//			| rdfs:comment@el = Στυλ κινουμένων σχεδίων με καταγωγή την Ιαπωνία}}
//
//			<ref name="anime">http://en.wikipedia.org/wiki/Anime</ref>
//			==References==
//			<references/>
	}
}
