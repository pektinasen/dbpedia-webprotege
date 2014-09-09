package ch.gennri.dpw;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.ParseException;

public class Tokenizer{
	private Reader source;
	private int currentChar;
	private StringBuilder builder;

	public Tokenizer(Reader source) {
		if (source == null)
			throw new IllegalArgumentException("source");

		this.source = source;
		this.builder = new StringBuilder();
	}

	public Token nextToken() throws ParseException
	{
	    skipWhitespaceAndNewline();

	    if (endOfStream())
	        return null;

	    if (Character.isAlphabetic(currentChar))
	        return readWord();

	    return readSymbol();
	
	}
	    
	private void nextChar() {
		try {
			currentChar = source.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private Token readWord() {
		return null;
	}
	
	private Token readSymbol() throws ParseException {
		nextChar();
		int firstChar = currentChar;
		if (firstChar != '{') {
			throw new ParseException("", 1);
		}
		nextChar();
		int secondChar = currentChar;
		if (secondChar != '{') {
			throw new ParseException("", 1);
		};
		return new Token(TokenType.Symbol, "{{");
	}
	
	private void skipWhitespaceAndNewline() {
		while (currentChar >= 0 && currentChar <= ' ')
			nextChar();
	}
	
	void storeCurrentCharAndReadNext()
	{
	    builder.append(currentChar);
	    nextChar();
	}
	
	private boolean endOfStream() {
		return currentChar < 0;
	}
	
	private String extractStoredChars()
	{
	    String value = builder.toString();
	    builder = new StringBuilder();
	    return value;
	}
}
enum TokenType {
	None, Word, Symbol, String;
}

class Token { 
	public final TokenType type;
	public final String value;
	
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
}