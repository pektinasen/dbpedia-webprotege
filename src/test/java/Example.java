import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Example {

	@Element(name="foobar")
	private String text;

	@Attribute
	private int index;
	
	@ElementList(entry="annotation")
	private
	List<String> annotations;

	@ElementList(inline=true)
	private List<Foo> foos;
	
	
	public List<Foo> getFoos() {
		return foos;
	}

	public void setFoos(List<Foo> foos) {
		this.foos = foos;
	}

	public Example() {
		super();
	}

	public Example(String text, int index, List<String> annotations) {
		this.text = text;
		this.index = index;
		this.setAnnotations(annotations);
	}

	public String getMessage() {
		return text;
	}

	public int getId() {
		return index;
	}

	public List<String> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}
}

class Foo {
	@Element
	String bla;

	public Foo(String bla) {
		super();
		this.bla = bla;
	}

	public String getBla() {
		return bla;
	}

	public void setBla(String bla) {
		this.bla = bla;
	}
}