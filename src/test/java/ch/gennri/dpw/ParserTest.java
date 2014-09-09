package ch.gennri.dpw;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

public class ParserTest {

	@Test
	public void testName() throws Exception {
		String text = "{{Class" +
		"	| rdfs:label@en = album"+
		"	| rdfs:label@de = Album"+
		"	| rdfs:label@fr = album"+
		"	| rdfs:label@el = album"+
		"	| rdfs:label@ko = 앨범 "+
		"	| rdfs:label@ja = アルバム"+
		"	| rdfs:label@nl = album"+
		"	| rdfs:label@pt = álbum"+
		"	| rdfs:label@zh = 照片集"+
		"	| labels = {{label|it|album}}"+
		"	| rdfs:subClassOf = MusicalWork"+
		"	| owl:equivalentClass = schema:MusicAlbum , wikidata:Q482994"+
		"	}}";

		StringReader stringReader = new StringReader(text);
		new Tokenizer(stringReader);
	}
	
}
