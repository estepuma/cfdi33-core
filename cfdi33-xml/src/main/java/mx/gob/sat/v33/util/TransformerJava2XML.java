/**
 * 
 */
package mx.gob.sat.v33.util;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import mx.gob.sat.v33.Comprobante;

/**
 * @author estepuma@hotmail.com
 *
 */
public interface TransformerJava2XML {
	public String java2XMLVersion3_3(Comprobante comprobante) throws IOException, ParserConfigurationException, TransformerException, Exception;
	
	public byte[] java2XMLVersion3_3BytesArr(Comprobante comprobante) throws IOException, ParserConfigurationException, TransformerException, Exception;
}
