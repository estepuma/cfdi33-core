/**
 * 
 */
package mx.gob.sat.v33.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import mx.gob.sat.v33.Comprobante;

/**
 * @author estepuma@hotmail.com
 *
 */
@Component
public class TransformerJava2XMLImpl implements TransformerJava2XML {
	private Logger logger = Logger.getLogger(TransformerJava2XMLImpl.class);
	
	private final Map<String, String> prefijos = new HashMap<String, String>();
	private Marshaller marshaller = null;
	
	public TransformerJava2XMLImpl() throws JAXBException {
		prefijos.put("http://www.w3.org/2001/XMLSchema-instance","xsi"); 
		prefijos.put("http://www.sat.gob.mx/cfd/3", "cfdi");
		prefijos.put("http://www.sat.gob.mx/TimbreFiscalDigital", "tfd");
		JAXBContext jaxbContext = JAXBContext.newInstance("mx.gob.sat.v33");
		marshaller = jaxbContext.createMarshaller();
		
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapperImpl(prefijos));
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
	}
	
	
	public String java2XMLVersion3_3(Comprobante comprobante) throws IOException, ParserConfigurationException, TransformerException, Exception {
		long time = System.currentTimeMillis();
		
		DocumentBuilderFactory docBuilderFac = DocumentBuilderFactory.newInstance();
		docBuilderFac.setNamespaceAware(true);
		DocumentBuilder db = null;
		db = docBuilderFac.newDocumentBuilder();
		
		Document doc = db.newDocument();
		marshaller.marshal(comprobante,doc);
		
		DOMSource domSource = new DOMSource(doc);
		
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        
		transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		
        String xmlResult = new String(writer.toString().getBytes());
        
        logger.debug("** Transformación de java a xml " + DurationFormatUtils.formatDuration(System.currentTimeMillis() - time, "HH:mm:ss:SS"));
        
		return xmlResult;
	}


	/* (non-Javadoc)
	 * @see mx.gob.sat.v33.util.TransformerJava2XML#java2XMLVersion3_3BytesArr(mx.gob.sat.v33.Comprobante)
	 */
	@Override
	public byte[] java2XMLVersion3_3BytesArr(Comprobante comprobante)
			throws IOException, ParserConfigurationException, TransformerException, Exception {
long time = System.currentTimeMillis();
		
		DocumentBuilderFactory docBuilderFac = DocumentBuilderFactory.newInstance();
		docBuilderFac.setNamespaceAware(true);
		DocumentBuilder db = null;
		db = docBuilderFac.newDocumentBuilder();
		
		Document doc = db.newDocument();
		marshaller.marshal(comprobante,doc);
		
		DOMSource domSource = new DOMSource(doc);
		
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        
		transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		
        byte[] xmlResultArr = writer.toString().getBytes();
        
        logger.debug("** Java transformation to XML in " + DurationFormatUtils.formatDuration(System.currentTimeMillis() - time, "HH:mm:ss:SS"));
        
		return xmlResultArr;
	}

}
