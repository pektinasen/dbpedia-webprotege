package ch.gennri.dpw.ws;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;

import ch.gennri.dpw.bot.TokenBot;
import ch.gennri.dpw.xml.Annotation;
import ch.gennri.dpw.xml.Class;
import ch.gennri.dpw.xml.Property;

public class WebProtegeController {

	private static final String VALUE_SEP = " , ";

	public String convert(Class clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append("{{Class\n");
		addLabelsAndComments(sb, clazz.getAnnotations());
		if (!clazz.getSub_classes().isEmpty()) {
			sb.append("| rdfs:subClassOf = ");
			for (String subclass : clazz.getSub_classes()) {
				sb.append(subclass).append(VALUE_SEP);
			}
			sb.delete(sb.length() - VALUE_SEP.length(), sb.length()).append("\n");
		}
		if (!clazz.getEquivalent_classes().isEmpty()) {
			sb.append("| owl:equivalentClass = ");
			for (String equivalentClass : clazz.getEquivalent_classes()) {
				sb.append(equivalentClass).append(VALUE_SEP);
			}
			sb.delete(sb.length() - VALUE_SEP.length(), sb.length()).append("\n");
		}
		if (!clazz.getDisjoint_classes().isEmpty()) {
			sb.append("| owl:disjointWith = ");
			for (String disjointClass : clazz.getDisjoint_classes()) {
				sb.append(disjointClass).append(VALUE_SEP);
			}
			sb.delete(sb.length() - VALUE_SEP.length(), sb.length()).append("\n");
		}
		sb.append("}}");
		return sb.toString();
	}

	public String convert(Property property, String propertyType) {
		StringBuilder sb = new StringBuilder();
		sb.append("{{"+propertyType+"\n");
		addLabelsAndComments(sb, property.getAnnotations());
		if (!property.getRanges().isEmpty()) {
			sb.append("| rdfs:range = ");
			for (String range : property.getRanges()) {
				sb.append(range).append(VALUE_SEP);
			}
			sb.delete(sb.length() - VALUE_SEP.length(), sb.length()).append("\n");
		}
		if (!property.getDomains().isEmpty()) {
			sb.append("| rdfs:domain = ");
			for (String domain : property.getDomains()) {
				sb.append(domain).append(VALUE_SEP);
			}
			sb.delete(sb.length() - VALUE_SEP.length(), sb.length()).append("\n");
		}
		if (!property.getSub_properties().isEmpty()) {
			sb.append("| rdfs:subPropertyOf = ");
			for (String subProperty : property.getSub_properties()) {
				sb.append(subProperty).append(VALUE_SEP);
			}
			sb.delete(sb.length() - VALUE_SEP.length(), sb.length()).append("\n");
		}
		if (!property.getEquivalent_Properties().isEmpty()) {
			sb.append("| owl:equivalentProperty = ");
			for (String equivalentProperty : property.getEquivalent_Properties()) {
				sb.append(equivalentProperty).append(VALUE_SEP);
			}
			sb.delete(sb.length() - VALUE_SEP.length(), sb.length()).append("\n");
		}
		if (!property.getDisjoint_Properties().isEmpty()) {
			sb.append("| owl:propertyDisjointWith = ");
			for (String disjointProperty : property.getDisjoint_Properties()) {
				sb.append(disjointProperty).append(VALUE_SEP);
			}
			sb.delete(sb.length()- VALUE_SEP.length(), sb.length()).append("\n");
		}
		sb.append("}}");

		return sb.toString();
	}

	private void addLabelsAndComments(StringBuilder sb, List<Annotation> annotations) {
		List<Annotation> labels = new LinkedList<>();
		List<Annotation> comments = new LinkedList<>();
		sort(annotations, labels, comments);
		if (!labels.isEmpty()) {
			sb.append("|labels =\n");
			for (Annotation a : labels) {
				sb.append("{{label|");
				// ex.: rdfs:label@en
				String[] split = a.getType().split("@");
				if (split.length > 1) {
					sb.append(split[1]);
				}
				sb.append("|");
				sb.append(a.getAnnotation()).append("}}\n");
			}
		}
		if (!comments.isEmpty()) {
			sb.append("|comment =\n");
			for (Annotation a : comments) {
				sb.append("{{comment|");
				// ex.: rdfs:comment@en
				String[] split = a.getType().split("@");
				if (split.length > 1) {
					sb.append(split[1]);
				}
				sb.append("|");
				sb.append(a.getAnnotation()).append("}}\n");
			}
		}
	}

	private void sort(List<Annotation> input, List<Annotation> labels, List<Annotation> comments) {
		for (Annotation a : input) {
			if (a.getType().startsWith("rdfs:label")) {
				labels.add(a);
			} else if (a.getType().startsWith("rdfs:comment")) {
				comments.add(a);
			} else {
				throw new IllegalArgumentException(a.toString());
			}
		}
	}

	public Response sendToDbpedia(String template, String propertyName) {
		// TODO Auto-generated method stub
		return Response.ok().build();
	}
    public Response sendToDbpediawithCredentials(String template, String propertyName, String token, String session_name, String session_id) {
        // TODO Auto-generated method stub
        String wiki_host = "http://160.45.114.250/mediawiki/";
        TokenBot tokenBot = new TokenBot(session_name, session_id, wiki_host);
        tokenBot.save_article(propertyName, template, token);
        return Response.ok().build();
    }
}
