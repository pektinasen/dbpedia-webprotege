package ch.gennri.dpw.xml;

import java.util.List;

import org.simpleframework.xml.Element;
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
	
	public List<Class> getClasses() {
		return classes;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}

	public List<Property> getObjectProperties() {
		return objectProperties;
	}

	public void setObjectProperties(List<Property> objectProperties) {
		this.objectProperties = objectProperties;
	}

	public List<Property> getDataProperties() {
		return dataProperties;
	}

	public void setDataProperties(List<Property> dataProperties) {
		this.dataProperties = dataProperties;
	}
	
}
