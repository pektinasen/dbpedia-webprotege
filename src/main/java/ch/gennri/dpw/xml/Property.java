package ch.gennri.dpw.xml;

import java.util.List;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.ElementList;

public class Property extends XmlElement{

	@ElementList(entry="sub_property", required=false)
	List<String> sub_properties;
	
	@ElementList(entry="domain", required=false)
	List<String> domains;
	
	@ElementList(entry="range", required=false)
	List<String> ranges;
	
	public Property() {
		super();
	};
	
	public Property(String IRI, String op, String changed_by) {
		super(IRI, op, changed_by);
	}
	public List<String> getSub_properties() {
		return sub_properties;
	}

	public void setSub_properties(List<String> sub_properties) {
		this.sub_properties = sub_properties;
	}

	public List<String> getDomains() {
		return domains;
	}

	public void setDomains(List<String> domains) {
		this.domains = domains;
	}

	public List<String> getRanges() {
		return ranges;
	}

	public void setRanges(List<String> ranges) {
		this.ranges = ranges;
	}
	
	
}
