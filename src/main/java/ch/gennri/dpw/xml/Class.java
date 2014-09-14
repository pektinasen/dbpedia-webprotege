package ch.gennri.dpw.xml;

import java.util.LinkedList;
import java.util.List;

import org.simpleframework.xml.ElementList;

public class Class extends XmlElement {

	@ElementList(entry="sub_class", required=false)
	List<String> sub_classes;
	
	@ElementList(entry="equivalent_class", required=false)
	List<String> equivalent_classes;
	
	@ElementList(entry="disjoint_class", required=false)
	List<String> disjoint_classes;
	
	public Class(){
		super();
	};
	
	public Class(String IRI,
			String op,
			String changed_by) {
		super(IRI, op, changed_by);
	}

	public List<String> getSub_classes() {
		if (sub_classes == null) {
			sub_classes = new LinkedList<>();
		}
		return sub_classes;
	}

	public void setSub_classes(List<String> sub_classes) {
		this.sub_classes = sub_classes;
	}

	public List<String> getEquivalent_classes() {
		if (equivalent_classes == null) {
			equivalent_classes = new LinkedList<>();
		}
		return equivalent_classes;
	}

	public void setEquivalent_classes(List<String> equivalent_classes) {
		this.equivalent_classes = equivalent_classes;
	}

	public List<String> getDisjoint_classes() {
		if (disjoint_classes == null) {
			disjoint_classes = new LinkedList<>();
		}
		return disjoint_classes;
	}

	public void setDisjoint_classes(List<String> disjoint_classes) {
		this.disjoint_classes = disjoint_classes;
	}
}
