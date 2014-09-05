import static org.junit.Assert.*;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ElementTraversal;

public class SimpleXmlTest {
	
}

class @Root
public class Example {

	   @Element
	   private String text;

	   @Attribute
	   private int index;

	   public Example() {
	      super();
	   }  

	   public Example(String text, int index) {
	      this.text = text;
	      this.index = index;
	   }

	   public String getMessage() {
	      return text;
	   }

	   public int getId() {
	      return index;
	   }
	}
