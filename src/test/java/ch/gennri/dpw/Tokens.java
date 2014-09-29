package ch.gennri.dpw;

public class Tokens {

	public static final Token templateBegin = new Token(TokenType.Symbol, "{{");
	public static final Token templateEnd = new Token(TokenType.Symbol, "}}");
	public static final Token symbolParametSep = new Token(TokenType.Symbol, "|");
	public static final Token symbolEquals = new Token(TokenType.Symbol, "=");
	public static final Token symbolComma = new Token(TokenType.Symbol, ",");
	
	public static final Token wordClass = new Token(TokenType.Name, "Class");
	public static final Token wordDataproperty = new Token(TokenType.Name, "Dataproperty");
	public static final Token wordObjectproperty = new Token(TokenType.Name, "ObjectProperty");
	
	
	public static final Token rdfsLabelEn = new Token(TokenType.Name, "rdfs:label@en");
	public static final Token rdfsSubclassOf = new Token(TokenType.Name, "rdfs:subClassOf");
	public static final Token owlDistinct = new Token(TokenType.Name, "owl:distinctClass");
	public static final Token owlEquivalent = new Token(TokenType.Name, "owl:equivalentClass");
	public static final Token labels = new Token(TokenType.Name, "labels");
	public static final Token comments = new Token(TokenType.Name, "comments");
	public static final Token label = new Token(TokenType.Name, "label");
	public static final Token comment = new Token(TokenType.Name, "comment");
	
	public static final Token album = new Token(TokenType.Name, "album");
	public static final Token schemaMusicAlbum = new Token(TokenType.Name, "schema:MusicAlbum");
	public static final Token wikidata = new Token(TokenType.Name, "wikidata:Q482994");
	public static final Token musicalWork = new Token(TokenType.Name, "MusicalWork");
	public static final Token nameEn = new Token(TokenType.Name, "en");
	public static final Token nameDe = new Token(TokenType.Name, "de");
	public static final Token nameSchool = new Token(TokenType.Name, "School");
	public static final Token nameSchule = new Token(TokenType.Name, "Schule");
	public static final Token commentSchool1 = new Token(TokenType.Name, "school");
	public static final Token commentSchool2 = new Token(TokenType.Name, "a");
	public static final Token commentSchool3 = new Token(TokenType.Name, "person");
	public static final Token commentSchool4 = new Token(TokenType.Name, "goes");
	public static final Token commentSchool5 = new Token(TokenType.Name, "to");
	public static final Token equivalentClasses = new Token(TokenType.Name, "schema:MusicAlbum , wikidata:Q482994");
	
}
