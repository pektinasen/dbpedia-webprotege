package ch.gennri.dpw;

public class Tokens {

	public static final Token templateBegin = new Token(TokenType.Symbol, "{{");
	public static final Token templateEnd = new Token(TokenType.Symbol, "}}");
	public static final Token parameterSeperator = new Token(TokenType.Symbol, "|");
	public static final Token wordClass = new Token(TokenType.Name, "Class");
	public static final Token rdfsLabelEn = new Token(TokenType.Name, "rdfs:label@en");
	public static final Token album = new Token(TokenType.Name, "album");
	public static final Token rdfsSubclassOf = new Token(TokenType.Name, "rdfs:subClassOf");
	public static final Token equals = new Token(TokenType.Symbol, "=");
	public static final Token comma = new Token(TokenType.Symbol, ",");
	public static final Token schemaMusicAlbum = new Token(TokenType.Name, "schema:MusicAlbum");
	public static final Token wikidata = new Token(TokenType.Name, "wikidata:Q482994");
	public static final Token musicalWork = new Token(TokenType.Name, "MusicalWork");
	public static final Token owlToken = new Token(TokenType.Name, "owl:equivalentClass");
	
}
