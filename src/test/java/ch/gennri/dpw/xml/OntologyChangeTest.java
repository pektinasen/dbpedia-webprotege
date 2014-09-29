package ch.gennri.dpw.xml;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.strategy.Visitor;
import org.simpleframework.xml.strategy.VisitorStrategy;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputNode;

public class OntologyChangeTest {

	
	public final String PIZZA_BASE_IRI = "http://www.pizza.com/ontologies/pizza.owl#PizzaBase";
	
	public final String OPERATION_ADD = "add";
	public final String OPERATION_REMOVE = "remove";
	
	@Test
	public void serializePizzaExample() throws Exception {
		final OntologyChange ontologyChange = createOntologyChangeObject();
		
		VisitorStrategy strategy = new VisitorStrategy(new Visitor() {
			
			@Override
			public void write(Type type, NodeMap<OutputNode> node) throws Exception {
				if (java.util.List.class.equals(type.getType())) {
					node.remove("class");
				}
			}
			
			@Override
			public void read(Type type, NodeMap<InputNode> node) throws Exception {
				// TODO Auto-generated method stub
				
			}
		});
		
		Format format = new Format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"); 
		Serializer serializer = new Persister(strategy,format);
		StringWriter sw = new StringWriter();
		serializer.write(ontologyChange, sw);
		
		
		String fileToString = FileUtils.readFileToString(new File("src/test/resources/pizzaTest.xml"));
		assertEquals(fileToString, sw.toString());
	}

	private OntologyChange createOntologyChangeObject() {
		final OntologyChange ontologyChange = new OntologyChange();
		final List<Class> classes = new LinkedList<>();
		final List<Annotation> annotations = new ArrayList<>();
		annotations.add(new Annotation("rdfs:label@de", "Pizza"));
		annotations.add(new Annotation("rdfs:comment", "Pizza ..."));

		final List<String> sub_classes = Arrays.asList(new String[]{
				"http://www.pizza.com/ontologies/pizza.owl#DeepPanBase",
				"http://www.pizza.com/ontologies/pizza.owl#ThinAndCrispyBase"
		
		});
		final Class classOne = new Class(PIZZA_BASE_IRI, OPERATION_ADD, "laparmakerli");
		classOne.setAnnotations(annotations);
		classOne.setSub_classes(sub_classes);
		Class classTwo = new Class("...stanford.edu/TestClass", OPERATION_REMOVE, "laparmakerli");
		classes.add(classOne);
		classes.add(classTwo);
		final Property objectPropertyOne = new Property(
				"http://www.pizza.com/ontologies/pizza.owl#hasIngredient",
				OPERATION_ADD,
				"laparmakerli");
		objectPropertyOne.setSub_properties(
				Arrays.asList(new String[]{
						"http://www.pizza.com/ontologies/pizza.owl#hasBase",
						"http://www.pizza.com/ontologies/pizza.owl#hasTopping"
				}));
		
		final Property objectPropertyTwo = new Property(
				"http://www.pizza.com/ontologies/pizza.owl#isBaseOf",
				OPERATION_ADD,
				"laparmakerli");
		objectPropertyTwo.setDomains(
				Arrays.asList(new String[]{
						"http://www.pizza.com/ontologies/pizza.owl#PizzaBase"
				}));
		
		objectPropertyTwo.setRanges(
				Arrays.asList(new String[]{
						"http://www.pizza.com/ontologies/pizza.owl#Pizza"
				}));
		
		final List<Property> objectProperties = new LinkedList<>();
		objectProperties.add(objectPropertyOne);
		objectProperties.add(objectPropertyTwo);
		
		
		final Property dataProperty = new Property(
				"dataProperty",
				OPERATION_ADD, "laparmakerli");
		dataProperty.setDomains(
				Arrays.asList(new String[]{
						"http://www.pizza.com/ontologies/pizza.owl#Pizza"
				}));
		
		dataProperty.setRanges(
				Arrays.asList(new String[]{
						"xsd:float"
				}));
		
		final List<Property> dataProperties = new LinkedList<>();
		dataProperties.add(dataProperty);
		
		ontologyChange.setClasses(classes);
		ontologyChange.setObjectProperties(objectProperties);
		ontologyChange.setDataProperties(dataProperties);
		return ontologyChange;
	}
	
	@Test
	public void readPizzaTest() throws Exception {
		Persister serializer = new Persister();
		OntologyChange read = serializer.read(OntologyChange.class, new File("src/test/resources/pizzaTest.xml"));
		assertNotNull(read);
	}
	
	@Test
	public void persisterTest() throws Exception {
		OntologyChange oc = createOntologyChangeObject();
		OntologyChangePersister persister = new OntologyChangePersister();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			persister.write(oc, stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ocXml = new String(stream.toByteArray());
		System.out.println(ocXml);
	}
}
