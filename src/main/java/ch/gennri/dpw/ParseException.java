package ch.gennri.dpw;

import java.io.IOException;

public class ParseException extends Exception {

	private String message;
	private int line;
	private int index;

	public ParseException(String message, int line, int index) {
		this(message, line, index, null);
	}

	public ParseException(String message, int line, int index, Exception e) {
		super(message + "line " + line +", index " + index, e);
		
		this.message = message;
		this.line = line;
		this.index = index;
	}
}
