package ch.gennri.dpw;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TokenizerTest {

	@Test
	public void testName() throws Exception {
		// Given
		String text = FileUtils.readFileToString(new File("src/test/resources/dbpedia_label.txt"));
		StringReader stringReader = new StringReader(text);
		Tokenizer tokenizer = new Tokenizer(stringReader);
		
		// When and Then
		Token templateBegin = new Token(TokenType.Symbol, "{{");
		Token templateEnd = new Token(TokenType.Symbol, "}}");
		Token parameterSeperator = new Token(TokenType.Symbol, "|");
		Token wordClass = new Token(TokenType.Name, "Class");
		Token rdfsLabelEn = new Token(TokenType.Name, "rdfs:label@en");
		Token album = new Token(TokenType.Name, "album");
		Token rdfsSubclassOf = new Token(TokenType.Name, "rdfs:subClassOf");
		Token equals = new Token(TokenType.Symbol, "=");
		Token comma = new Token(TokenType.Symbol, ",");
		Token schemaMusicAlbum = new Token(TokenType.Name, "schema:MusicAlbum , wikidata:Q482994");
		Token wikidata = new Token(TokenType.Name, "wikidata:Q482994");
		Token musicalWork = new Token(TokenType.Name, "MusicalWork");
		Token owlToken = new Token(TokenType.Name, "owl:equivalentClass");
		
		Token token1 = tokenizer.next();
		assertEquals(templateBegin , token1);
		
		Token token2 = tokenizer.next();
		assertEquals(wordClass , token2);
		
		Token token3 = tokenizer.next();
		assertEquals(parameterSeperator , token3);
		
		Token token4 = tokenizer.next();
		assertEquals(Tokens.labels , token4);
		
		Token token5 = tokenizer.next();
		assertEquals(equals , token5);
		
		Token token6 = tokenizer.next();
		assertEquals(templateBegin , token6);
		
		Token token7 = tokenizer.next();
		assertEquals(Tokens.label , token7);
		
		Token token8 = tokenizer.next();
		assertEquals(parameterSeperator , token8);
		
		Token token9 = tokenizer.next();
		assertEquals(Tokens.nameEn , token9);
		
		Token token10 = tokenizer.next();
		assertEquals(parameterSeperator , token10);
		
		Token token11 = tokenizer.next();
		assertEquals(Tokens.album , token11);
		
		Token token12 = tokenizer.next();
		assertEquals(templateEnd , token12);
		
		Token token13 = tokenizer.next();
		assertEquals(parameterSeperator , token13);
		
		Token token14 = tokenizer.next();
		assertEquals(rdfsSubclassOf , token14);
		
		Token token15 = tokenizer.next();
		assertEquals(equals , token15);
		
		Token token16 = tokenizer.next();
		assertEquals(musicalWork , token16);
		
		Token token17 = tokenizer.next();
		assertEquals(parameterSeperator , token17);
		
		Token token18 = tokenizer.next();
		assertEquals(owlToken , token18);
		
		Token token19 = tokenizer.next();
		assertEquals(equals , token19);
		
		Token token20 = tokenizer.next();
		assertEquals(schemaMusicAlbum , token20);
		
		Token token23 = tokenizer.next();
		assertEquals(templateEnd, token23);
	}
	
	@Test
	public void animeFile() throws Exception {
		Tokenizer tokenizer = new Tokenizer(new FileReader("src/test/resources/dbpedia_anime.txt"));
		Token t = null;
		List<Token> tokens = new LinkedList<>();
		while ((t = tokenizer.next()) != null) {
			tokens.add(t);
			System.out.println("Token: "  +t);
		}
		System.out.println(tokens);
		assertEquals(40, tokens.size());
	}
}
