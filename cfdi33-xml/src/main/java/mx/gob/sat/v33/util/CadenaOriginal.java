package mx.gob.sat.v33.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.ssl.PKCS8Key;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import mx.gob.sat.v33.Comprobante;

/**
 * @author estepuma@hotmail.com
 *
 */
@Component
public class CadenaOriginal {
	private Logger logger = Logger.getLogger(CadenaOriginal.class);
	
	private static final String PCKG_V33 = "mx.gob.sat.v33";
	private static final String CADENA_ORIGINAL_V33 = "/xslt33/cadenaoriginal_3_3.xslt";
	
	private static final String RSA = "RSA";
	private static final String FIRMA = "SHA256withRSA";
	
	@Value("${private.key.file.string}")
	private String keyFileString;
	
	@Value("${private.key.pwd}")
	private String keyWord;
	
	public byte[] getCadenaOriginal33(Comprobante comprobante) throws JAXBException, TransformerException, UnsupportedEncodingException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Result out = new StreamResult(baos);
	    
		JAXBSource in = new JAXBSource(JAXBContext.newInstance(PCKG_V33), comprobante);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformerFactory.setURIResolver(new URIResolverImpl());
		
		StreamSource ss = new StreamSource(getClass().getResourceAsStream(CADENA_ORIGINAL_V33));
		
		Transformer transformer = transformerFactory.newTransformer(ss);
		transformer.transform(in, out);
		
		return baos.toByteArray();
	}
	
	public String firmarCadenaOriginal(byte[] cadenaOriginal) throws GeneralSecurityException {

		byte []archivoKeyBytes = Base64.getDecoder().decode(keyFileString);
		
		PKCS8Key pkcs8 = new PKCS8Key(archivoKeyBytes, keyWord.toCharArray());
		KeyFactory privateKeyFactory = KeyFactory.getInstance(RSA);
		PKCS8EncodedKeySpec pkcs8Encoded = new PKCS8EncodedKeySpec(pkcs8.getDecryptedBytes());
		PrivateKey privateKey = privateKeyFactory.generatePrivate(pkcs8Encoded);

		Signature signature = Signature.getInstance(FIRMA);
		signature.initSign(privateKey);
		signature.update(cadenaOriginal);

		return new String(Base64.getEncoder().encode(signature.sign()));
	}
}
