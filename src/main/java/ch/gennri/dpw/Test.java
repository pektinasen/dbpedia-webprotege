package ch.gennri.dpw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		final Pattern rdfsLabel = Pattern.compile("(rdfs:label)(?:@([a-z]{2}))?");
		Matcher matcher = rdfsLabel.matcher("rdfs:label@en");
		System.out.println(matcher.matches());
		System.out.println(matcher.group(0));
		System.out.println(matcher.group(1));
		System.out.println(matcher.group(2));
	}
}
