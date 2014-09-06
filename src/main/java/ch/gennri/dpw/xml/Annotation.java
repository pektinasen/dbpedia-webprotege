package ch.gennri.dpw.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root
public class Annotation {

	@Attribute
	private String type;
	
	@Text
	private String text;

	public Annotation(){};
	
	public Annotation(String type, String text) {
		super();
		this.type = type;
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAnnotation() {
		return text;
	}

	public void setAnnotation(String annotation) {
		this.text = annotation;
	}
}
