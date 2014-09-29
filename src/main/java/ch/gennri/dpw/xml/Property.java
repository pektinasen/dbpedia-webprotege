package ch.gennri.dpw.xml;

import java.util.List;

import org.simpleframework.xml.ElementList;

public class Property extends XmlElement{

	@ElementList(entry="sub_property", required=false)
	List<String> sub_properties;
	
	@ElementList(entry="domain", required=false)
	List<String> domains;
	
	@ElementList(entry="range", required=false)
	List<String> ranges;

	@ElementList(entry="disjoint_property", required=false)
	List<String> disjoint_properties;

	@ElementList(entry="equivalent_property", required=false)
	List<String> equivalent_properties;
	
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
	
	public void setDisjoint_properties(List<String> disjoint_properties) {
		this.disjoint_properties = disjoint_properties;
	}


	public List<String> getDisjoint_Properties() {
		return disjoint_properties;
	}

	public void setEquivalent_properties(List<String> equivalent_properties) {
		this.equivalent_properties = equivalent_properties;
	}

	public List<String> getEquivalent_Properties() {
		return equivalent_properties;
	}
	
	
}
