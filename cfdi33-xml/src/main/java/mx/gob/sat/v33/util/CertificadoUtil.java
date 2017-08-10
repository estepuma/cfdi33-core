/**
 * 
 */
package mx.gob.sat.v33.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author estepuma@hotmail.com
 *
 */
@Component
public class CertificadoUtil {

	private Logger logger = Logger.getLogger(CertificadoUtil.class);
	
	private static final String X_509 = "X.509";
	
	@Value("${cert.file.path}")
	private String certFilePath;
	
	@Value("${private.key.file.path}")
	private String privateFilePath;
	
	public String getNoCertificado() throws FileNotFoundException, CertificateException {
		
		InputStream is = new FileInputStream(certFilePath);
		CertificateFactory cf = CertificateFactory.getInstance(X_509);
		X509Certificate certificado=(X509Certificate)cf.generateCertificate(is);
		byte[] byteArray= certificado.getSerialNumber().toByteArray();
        String noCertificado = new String(byteArray);

		
		return noCertificado;
	}
	
	private String getFileString(String path) throws IOException {
		File file = new File(path);
		FileInputStream fileInputStream = null;
		String fileString = null;
		
		fileInputStream = new FileInputStream(file);
		byte[] fileBytes = new byte[fileInputStream.available()];
		fileInputStream.read(fileBytes);
		fileInputStream.close();
		
		fileString = new String(Base64.getEncoder().encode(fileBytes));
		
		return fileString;
	}
	
	public String getCertificado() throws IOException {
		return getFileString(certFilePath);
	}
	
	public String getPrivateFile() throws IOException {
		return getFileString(privateFilePath);
	}

}
