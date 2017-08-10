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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.time.DurationFormatUtils;
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
public class CadenaOriginalImpl implements CadenaOriginal {
	private Logger logger = Logger.getLogger(CadenaOriginalImpl.class);
	
	private static final String PCKG_V33 = "mx.gob.sat.v33";
	private static final String CADENA_ORIGINAL_V33 = "/xslt33/cadenaoriginal_3_3.xslt";
	
	private static final String RSA = "RSA";
//	private static final String FIRMA = "SHA256withRSA";
	private static final String FIRMA = "SHA256withRSA";
	
	private JAXBContext jaxBContext = null;
	private Transformer transformer = null;
	
	private Signature signature = null;
	
	public CadenaOriginalImpl(@Value("${private.key.file.string}") String keyFileString, @Value("${private.key.pwd}") String keyWord) throws JAXBException, GeneralSecurityException, TransformerConfigurationException {
//		Cadena Original
		jaxBContext = JAXBContext.newInstance(PCKG_V33);
		StreamSource ss = new StreamSource(getClass().getResourceAsStream(CADENA_ORIGINAL_V33));
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformerFactory.setURIResolver(new URIResolverImpl());
		transformer = transformerFactory.newTransformer(ss);
		
//		Firma
		PKCS8Key pkcs8 = new PKCS8Key(Base64.getDecoder().decode(keyFileString), keyWord.toCharArray());
		KeyFactory privateKeyFactory = KeyFactory.getInstance(RSA);
		PKCS8EncodedKeySpec pkcs8Encoded = new PKCS8EncodedKeySpec(pkcs8.getDecryptedBytes());
		PrivateKey privateKey = privateKeyFactory.generatePrivate(pkcs8Encoded);

		signature = Signature.getInstance(FIRMA);
		signature.initSign(privateKey);
	}
	
	public byte[] getCadenaOriginal33(Comprobante comprobante) throws JAXBException, UnsupportedEncodingException, TransformerException {
		long time = System.currentTimeMillis();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Result out = new StreamResult(baos);
	    
		JAXBSource in = new JAXBSource(jaxBContext, comprobante);
		
		transformer.transform(in, out);
		
		byte[] cadenaOriginalArray = baos.toByteArray();
		
		logger.debug("** Tiempo de generacion de cadena original: " + DurationFormatUtils.formatDuration(System.currentTimeMillis() - time, "HH:mm:ss:SS"));
		
		return cadenaOriginalArray;
	}
	
	public String firmarCadenaOriginal(byte[] cadenaOriginal) throws GeneralSecurityException {
		long time = System.currentTimeMillis();
		
		signature.update(cadenaOriginal);
		String firma = new String(Base64.getEncoder().encode(signature.sign()));
		
		logger.debug("** Tiempo de generacion de firma de cadena original: " + DurationFormatUtils.formatDuration(System.currentTimeMillis() - time, "HH:mm:ss:SS"));
		
		return firma;
	}
}
