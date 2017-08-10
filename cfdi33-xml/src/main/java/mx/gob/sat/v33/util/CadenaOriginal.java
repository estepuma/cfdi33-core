/**
 * 
 */
package mx.gob.sat.v33.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import mx.gob.sat.v33.Comprobante;

/**
 * @author estepuma@hotmail.com
 *
 */
public interface CadenaOriginal {
	
	public byte[] getCadenaOriginal33(Comprobante comprobante) throws JAXBException, TransformerException, UnsupportedEncodingException;
	
	public String firmarCadenaOriginal(byte[] cadenaOriginal) throws GeneralSecurityException;
}
