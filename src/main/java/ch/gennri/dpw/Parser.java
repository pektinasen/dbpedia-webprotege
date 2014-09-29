package ch.gennri.dpw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.gennri.dpw.xml.Annotation;
import ch.gennri.dpw.xml.Class;
import ch.gennri.dpw.xml.OntologyChange;
import ch.gennri.dpw.xml.Property;
import ch.gennri.dpw.xml.XmlElement;

/**
 * With the tokens returned by the Tokenizer, the parser builds an OntologyChange Object.
 * 
 * @author Sascha
 *
 */
public class Parser {

	private static Logger logger = LoggerFactory.getLogger(Parser.class);

	private Tokenizer tokenizer;
	private OntologyChange oc;
	private List<String> validTemplateNames = Arrays.asList("Class",
			"DatatypeProperty", "ObjectProperty");

	private Token currentToken;

	private static final Pattern rdfsLabel = Pattern.compile("rdfs:label(?:@[a-z]{2})?");
	private static final Pattern rdfsComment = Pattern.compile("rdfs:comment(?:@[a-z]{2})?");
	private static final Pattern rdfsSubClassOf = Pattern.compile("rdfs:subClassOf");
	private static final  Pattern rdfsSubPropertyOf = Pattern.compile("rdfs:subPropertyOf");
	private static final Pattern owlEquivalentOf = Pattern.compile("owl:equivalentClass");
	
	public Parser(String input){
		this.tokenizer = new Tokenizer(new StringReader(input));
		this.oc = new OntologyChange();
	}
	public Parser(File file) throws FileNotFoundException {
		this.tokenizer = new Tokenizer(new FileReader(file));
		this.oc = new OntologyChange();
	}
	protected Parser(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
		this.oc = new OntologyChange();
	}

	public OntologyChange parse() throws ParseException {
		logger.debug("begin parsing");
		nextToken();
		if (!isBeginTemplate(currentToken)) {
			throw new ParseException("", tokenizer.getLine(),
					tokenizer.getIndex());
		}
		readTemplate();
		nextToken();
		if (currentToken != null &&
				currentToken.type == TokenType.Other) {
			oc.setAppendix(currentToken.value);
		}
		return oc;
	}

	private void readTemplate() throws ParseException {
		nextToken();
		if (isValidName(currentToken)) {
			switch (currentToken.value) {
			case "Class":
				readClass();
				break;
			case "DatatypeProperty":
				readDatatypeProperty();
				break;
			case "ObjectProperty":
				readObjectProperty();
				break;
			default:
				throw new ParseException("unknown template name",
						tokenizer.getLine(), tokenizer.getIndex());
			}
		}
	}

	private void readDatatypeProperty() throws ParseException {
		Property property = readProperty();
		oc.getDataProperties().add(property);
	}

	private Property readProperty() throws ParseException {
		logger.debug("readProperty");
		nextToken();
		Property property = new Property();
		while (isParameter(currentToken)) {
			parseParameter(property);
		}
		return property;
	}
	private void parseParameter(Property property) throws ParseException {
		nextToken();
		if (!currentToken.type.equals(TokenType.Name)) {
			throw new ParseException("", 0, 0);
		}
		if (rdfsLabel.matcher(currentToken.value).matches()){
			parseAnnotations(property);
		} else if ("labels".equals(currentToken.value)){
			nextToken();
			assertIsEqualSign();
			nextToken();
			assertBeginTemplate();
			while (isBeginTemplate(currentToken)) {
				parseLabelsAnnotations(property);
			}
		} else if ("comments".equals(currentToken.value)){
			nextToken();
			assertIsEqualSign();
			nextToken();
			assertBeginTemplate();
			while (isBeginTemplate(currentToken)) {
				parseCommentsAnnotations(property);
			}
		} else if (rdfsSubPropertyOf.matcher(currentToken.value).matches()) {
			parseSubPropertyOf(property);
		} 
	}

	private void parseSubPropertyOf(Property property) throws ParseException {
		nextToken();
		assertIsEqualSign();
		nextToken();
		assertIsName();
		String[] split = currentToken.value.split(",");
		for (String subProperty : split) {
			property.getSub_properties().add(subProperty.trim());
		}
		nextToken();
	}

	private void readObjectProperty() throws ParseException {
		Property property = readProperty();
		oc.getObjectProperties().add(property);
	}

	private void readClass() throws ParseException {
		Class clazz = new Class();
		nextToken();
		while (isParameter(currentToken)) {
			parseParameter(clazz);
		}
		oc.setClasses(Arrays.asList(clazz));
	}

	private void parseParameter(Class clazz) throws ParseException {
		nextToken();
		if (!currentToken.type.equals(TokenType.Name)) {
			throw new ParseException("", 0, 0);
		}
		if (rdfsLabel.matcher(currentToken.value).matches()){
			parseAnnotations(clazz);
		} else if (rdfsComment.matcher(currentToken.value).matches()){
			parseAnnotations(clazz);
		} else if ("labels".equals(currentToken.value)){
			parseLabelsAnnotations(clazz);
		} else if ("comments".equals(currentToken.value)){
			parseCommentsAnnotations(clazz);
		} else if (rdfsSubClassOf.matcher(currentToken.value).matches()) {
			parseSubClassOf(clazz);
		} else if (owlEquivalentOf.matcher(currentToken.value).matches()) {
			parseEquivalentClass(clazz);
		} 
	}

	private void parseCommentsAnnotations(XmlElement element) throws ParseException {
		Annotation annotation = new Annotation();
		nextToken();
		assertIsComment();
		nextToken();
		assertParameter();
		nextToken();
		if (isParameter(currentToken)) {
			annotation.setType("rdfs:comment");
		} else {
			annotation.setType("rdfs:comment@" + currentToken.value);
			nextToken();
		}
		assertParameter();
		nextToken();
		StringBuilder sb = new StringBuilder();
		while (!isEndTemplate()) {
			sb.append(currentToken.value);
			sb.append(" ");
			nextToken();
		}
		annotation.setAnnotation(sb.toString().trim());
		element.getAnnotations().add(annotation);
		assertEndTemplate();
		nextToken();
	}

	private void assertIsComment() throws ParseException {
		assertToken(new Token(TokenType.Name, "comment"));
	}

	private void assertEndTemplate() throws ParseException {
		if (!currentToken.equals(new Token(TokenType.Symbol, "}}"))) {
			throw new ParseException("end template expected", tokenizer.getLine(), tokenizer.getIndex());
		}
	}

	private void assertParameter() throws ParseException {
		if (!isParameter(currentToken)) {
			throw new ParseException("parameter seperator | expected", tokenizer.getLine(), tokenizer.getIndex());
		}
	}

	private void assertToken(Token token) throws ParseException {
		if (!currentToken.equals(token)) {
			throw new ParseException("something else expected", tokenizer.getLine(), tokenizer.getIndex());
		}
	}
	private void assertBeginTemplate() throws ParseException {
		if (!currentToken.equals(new Token(TokenType.Symbol, "{{"))) {
			throw new ParseException("begin template expected", tokenizer.getLine(), tokenizer.getIndex());
		}
	}

	private void parseLabelsAnnotations(XmlElement element) throws ParseException {
		Annotation annotation = new Annotation();
		nextToken();
		assertIsLabel();
		nextToken();
		assertParameter();
		nextToken();
		if (isParameter(currentToken)) {
			annotation.setType("rdfs:label");
		} else {
			annotation.setType("rdfs:label@" + currentToken.value);
			nextToken();
		}
		assertParameter();
		nextToken();
		StringBuilder sb = new StringBuilder();
		while (!isEndTemplate()) {
			sb.append(currentToken.value);
			sb.append(" ");
			nextToken();
		}
		annotation.setAnnotation(sb.toString().trim());
		element.getAnnotations().add(annotation);
		assertEndTemplate();
		nextToken();
	}

	private boolean isEndTemplate() {
		return new Token(TokenType.Symbol, "}}").equals(currentToken);
	}

	private void assertIsLabel() throws ParseException {
		assertToken(new Token(TokenType.Name, "label"));
	}

	private void parseSubClassOf(Class clazz) throws ParseException {
		nextToken();
		assertIsEqualSign();
		nextToken();
		assertIsName();
		String[] split = currentToken.value.split(",");
		for (String subClass : split) {
			clazz.getSub_classes().add(subClass.trim());
		}
		nextToken();
	}
	
	private void parseEquivalentClass(Class clazz) throws ParseException {
		nextToken();
		assertIsEqualSign();
		nextToken();
		assertIsName();
		String[] split = currentToken.value.split(",");
		for (String equivalentClass : split) {
			clazz.getEquivalent_classes().add(equivalentClass.trim());
		}
		nextToken();
	}
		
	private void assertIsName() throws ParseException {
		if (!currentToken.type.equals(TokenType.Name)) {
			throw new ParseException("", 0, 0);
		}
	}

	private void assertIsEqualSign() throws ParseException {
		if (!currentToken.type.equals(TokenType.Symbol) ||
				!currentToken.value.equals("=")) {
			throw new ParseException("expected = but was " + currentToken.value, tokenizer.getLine(), tokenizer.getIndex());
		}
		
	}

	private void nextToken() throws ParseException {
		currentToken = tokenizer.next();
		logger.debug("token: " + currentToken.value);
	}

	private void parseAnnotations(XmlElement element) throws ParseException {
		Annotation annotation = new Annotation();
		annotation.setType(currentToken.value);
		nextToken();
		assertIsEqualSign();
		nextToken();
		if (!currentToken.type.equals(TokenType.Name)) {
			throw new ParseException("", 0, 0);
		}
		annotation.setAnnotation(currentToken.value);
		element.getAnnotations().add(annotation);
		nextToken();
	}

	private boolean isParameter(Token token) {
		return (TokenType.Symbol.equals(token.type) &&
				"|".equals(token.value));
	}

	private boolean isValidName(Token token) {
		return (token.type == TokenType.Name && validTemplateNames
				.contains(token.value));
	}

	public OntologyChange serializeOntologyChange() {
		return null;
	}

	private boolean isBeginTemplate(Token token) {
		return new Token(TokenType.Symbol, "{{").equals(token);

	}
}
