package ch.gennri.dpw.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.gennri.dpw.xml.OntologyChange;
import ch.gennri.dpw.xml.OntologyChangePersister;

@Provider
@Produces("application/xml")
@Consumes("application/xml")
public class OntologyChangeProvider implements MessageBodyReader<OntologyChange>, MessageBodyWriter<OntologyChange> {

	private static Logger logger = LoggerFactory.getLogger(OntologyChangeProvider.class);
	
	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return type == OntologyChange.class;
	}

	@Override
	public long getSize(OntologyChange t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		// deprecated by JAX-RS 2.0 and ignored by Jersey runtime
		return 0;
	}

	@Override
	public void writeTo(OntologyChange t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		OntologyChangePersister persister = new OntologyChangePersister();
		try {
			persister.write(t, entityStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		logger.debug("is readable");
		return type == OntologyChange.class;
	}

	@Override
	public OntologyChange readFrom(Class<OntologyChange> type,
			Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		logger.debug("read from");
		OntologyChangePersister persister = new OntologyChangePersister();
		try {
			return persister.read(entityStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
