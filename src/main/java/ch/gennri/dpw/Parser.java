package ch.gennri.dpw;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.gennri.dpw.xml.Annotation;
import ch.gennri.dpw.xml.Class;
import ch.gennri.dpw.xml.OntologyChange;

public class Parser {

	private Tokenizer tokenizer;
	private OntologyChange oc;
	private List<String> validTemplateNames = Arrays.asList("Class",
			"DatatypeProperty", "ObjectProperty");

	private boolean rootTemplate;
	private Token currentToken;
	
	private static final Pattern rdfsLabel = Pattern.compile("rdfs:label(?:@[a-z]{2})?");
	private static final Pattern rdfsSubClassOf = Pattern.compile("rdfs:subClassOf");
	private static final Pattern owlEquivalentOf = Pattern.compile("owl:equivalentClass");
	
	
	public Parser(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
		this.oc = new OntologyChange();
		this.rootTemplate = true;
	}

	public OntologyChange parse() throws ParseException {
		currentToken = tokenizer.next();
		if (!isBeginTemplate(currentToken)) {
			throw new ParseException("", tokenizer.getLine(),
					tokenizer.getIndex());
		}
		readTemplate();
		return oc;
	}

	private void readTemplate() throws ParseException {
		currentToken = tokenizer.next();
		if (isValidName(currentToken)) {
			switch (currentToken.value) {
			case "Class":
				if (!rootTemplate) {
					throw new ParseException("expected \"label\" but found \"Class\"",
							tokenizer.getLine(), tokenizer.getIndex());
				} else {
					readClass();
				}
				break;
			case "DatatypeProperty":
			case "ObjectProperty":
				if (!rootTemplate) {
					throw new ParseException("expected \"label\" but found \""+ currentToken.value+"\"",
							tokenizer.getLine(), tokenizer.getIndex());
				}else {
					readProperty();
				}
				break;
			case "label":
				if (rootTemplate) {
					throw new ParseException("expected one of "+ validTemplateNames +" but found \"label\"",
							tokenizer.getLine(), tokenizer.getIndex());
				}
				break;
			default:
				throw new ParseException("unknown template name",
						tokenizer.getLine(), tokenizer.getIndex());
			}

		}

	}

	private void readProperty() {
		
	}

	private void readClass() throws ParseException {
		Class clazz = new Class();
		currentToken = tokenizer.next();
		while (isParameter(currentToken)) {
			parseParameter(clazz);
		}
		oc.setClasses(Arrays.asList(clazz));
	}

	private void parseParameter(Class clazz) throws ParseException {
		currentToken = tokenizer.next();
		if (!currentToken.type.equals(TokenType.Name)) {
			throw new ParseException("", 0, 0);
		}
		if (rdfsLabel.matcher(currentToken.value).matches()){
			parseAnnotations(clazz);
		} else if (rdfsSubClassOf.matcher(currentToken.value).matches()) {
			parseSubClassOf(clazz);
		} else if (owlEquivalentOf.matcher(currentToken.value).matches()) {
			parseEquivalentClass(clazz);
		} 
	}

	private void parseSubClassOf(Class clazz) throws ParseException {
		List<String> subclasses = new LinkedList<>();
		nextToken();
		assertIsEqualSign();
		nextToken();
		assertIsName();
		subclasses.add(currentToken.value);
		nextToken();
		while (isComma()) {
			nextToken();
			assertIsName();
			subclasses.add(currentToken.value);
		}
		clazz.getSub_classes().addAll(subclasses);
	}
	
	private void parseEquivalentClass(Class clazz) throws ParseException {
		LinkedList<String> equivalentClasses = new LinkedList<>();
		nextToken();
		assertIsEqualSign();
		nextToken();
		assertIsName();
		equivalentClasses.add(currentToken.value);
		nextToken();
		while (isComma()) {
			nextToken();
			assertIsName();
			equivalentClasses.add(currentToken.value);
		}
		clazz.getEquivalent_classes().addAll(equivalentClasses);
	}
	
	private boolean isComma() {
		return currentToken.type.equals(TokenType.Symbol) && 
				currentToken.value.equals(",");
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
	}

	private void parseAnnotations(Class clazz) throws ParseException {
		Annotation annotation = new Annotation();
		annotation.setType(currentToken.value);
		currentToken = tokenizer.next();
		assertIsEqualSign();
		currentToken = tokenizer.next();
		if (!currentToken.type.equals(TokenType.Name)) {
			throw new ParseException("", 0, 0);
		}
		annotation.setAnnotation(currentToken.value);
		clazz.getAnnotations().add(annotation);
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
