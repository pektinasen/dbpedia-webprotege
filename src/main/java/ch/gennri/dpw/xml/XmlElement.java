package ch.gennri.dpw.xml;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class XmlElement {

	@Attribute
	private String IRI;

	@Attribute
	private String changed_by;
	
	@Attribute
	private String op;

	@ElementList(entry="annotation", data=true, required=false)
	List<Annotation> annotations;
	
	public XmlElement() {
		super();
	}
	
	public XmlElement(String IRI, String changed_by, String op) {
		this.IRI = IRI;
		this.op = op;
		this.changed_by = changed_by;
	}

	public String getIRI() {
		return IRI;
	}

	public void setIRI(String iRI) {
		this.IRI = iRI;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getChanged_by() {
		return changed_by;
	}

	public void setChanged_by(String changed_by) {
		this.changed_by = changed_by;
	}
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}


}
