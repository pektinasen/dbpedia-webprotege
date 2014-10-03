package ch.gennri.dpw.xml;

import java.util.LinkedList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="ontology_change")
public class OntologyChange{

	@ElementList(entry="class", inline=true, required=false)
	List<Class> classes;

    @ElementList(entry="object_property", inline=true, required=false)
	List<Property> objectProperties;

	@ElementList(entry="data_property", inline=true, required=false)
	List<Property> dataProperties;

	private String appendix;

	public OntologyChange() {
	}

	public List<Class> getClasses() {
		if (classes == null) {
			classes = new LinkedList<>();
		}
		return classes;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}

	public List<Property> getObjectProperties() {
		if (objectProperties == null) {
			objectProperties = new LinkedList<>();
		}
		return objectProperties;
	}

	public void setObjectProperties(List<Property> objectProperties) {
		this.objectProperties = objectProperties;
	}

	public List<Property> getDataProperties() {
		if (dataProperties == null) {
			dataProperties = new LinkedList<>();
		}
		return dataProperties;
	}

	public void setDataProperties(List<Property> dataProperties) {
		this.dataProperties = dataProperties;
	}

	public String getAppendix() {
		return appendix;
	}

	public void setAppendix(String value) {
		this.appendix = value;
	}



}
