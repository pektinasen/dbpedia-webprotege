package ch.gennri.dpw;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Tokenizer{
	private Reader source;
	private int currentChar;
	private StringBuilder builder;
	private int line;
	private int index;
	
	private final Set<Character> symbols;

	public Tokenizer(Reader source) {
		if (source == null)
			throw new IllegalArgumentException("source");

		this.source = source;
		this.builder = new StringBuilder();
		this.symbols = new HashSet<>();
		symbols.add("{".charAt(0));
		symbols.add("}".charAt(0));
		symbols.add("|".charAt(0));
		symbols.add("=".charAt(0));
		symbols.add(",".charAt(0));
	}

	public Token next() throws ParseException {
		nextChar();
		skipWhitespaceAndNewline();
		if (endOfStream())
			return null;
		if (Character.isAlphabetic(currentChar))
			return readName();
		return readSymbol();
	}

	private void nextChar() throws ParseException {
		try {
			currentChar = source.read();
			System.out.print((char) currentChar);
			index++;
		} catch (IOException e) {
			throw new ParseException("Could not read next character", line, index, e);
		}
	}
	
	
	private Token readName() throws ParseException {
		storeCurrentCharAndReadNext();
		while (isPartOfWord(currentChar)){
			storeCurrentCharAndReadNext();
		}
	
		return new Token(TokenType.Name, extractStoredChars());
	}
	
	private boolean isPartOfWord(int character) {
		return	(Character.isLetterOrDigit(character) ||
				character == ':' |
				character == '@' );
	}

	private Token readSymbol() throws ParseException {
		boolean oneCurlyOpenBrace = false;
		boolean oneCurlyCloseBrace = false;
		while(isSymbol(currentChar)) {
			switch (currentChar) {
			case '{':
				if (oneCurlyOpenBrace) {
					return new Token(TokenType.Symbol, "{{");
				} else {
					oneCurlyOpenBrace = true;
				}
				break;
			case '}':
				if (oneCurlyCloseBrace) {
					return new Token(TokenType.Symbol, "}}");
				} else {
					oneCurlyCloseBrace = true;
				}
				break;
			case '|':
				if (oneCurlyOpenBrace || oneCurlyCloseBrace) {
					throw new ParseException("Expected second \"{\" or \"}\"", line, index);
				} else {
					return new Token(TokenType.Symbol, "|");
				}
			case '=':
				if (oneCurlyOpenBrace || oneCurlyCloseBrace) {
					throw new ParseException("Expected second \"{\" or \"}\"", line, index);
				} else {
					return new Token(TokenType.Symbol, "=");
				}
			case ',':
				if (oneCurlyOpenBrace || oneCurlyCloseBrace) {
					throw new ParseException("Expected second \"{\" or \"}\"", line, index);
				} else {
					return new Token(TokenType.Symbol, ",");
				}
			default:
				break;
			}
			nextChar();
		}
		return null;
	}
	
	private boolean isSymbol(int character) {
		return symbols.contains((char) character);
	}

	private void skipWhitespaceAndNewline() throws ParseException {
		while (Character.isWhitespace(currentChar)) {
			if (currentChar == '\n') {
				line++;
				index = 0;
			}
			nextChar();
		}
	}
	
	void storeCurrentCharAndReadNext() throws ParseException {
		builder.append((char) currentChar);
		nextChar();
	}
	
	private boolean endOfStream() {
		return currentChar < 0;
	}
	
	private String extractStoredChars() {
		String value = builder.toString();
		builder = new StringBuilder();
		return value;
	}
	
	
	public int getLine() {
		return this.line;
	}
	
	public int getIndex() {
		return this.index;
	}
}

enum TokenType {
	None, Name, Parameter, Symbol;
}

class Token { 
	public final TokenType type;
	public final String value;
	
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{type: " + type + ", value: " + value + "}";
	}
	
}