/**
 * 
 */
package mx.gob.sat.v33.util;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertificateException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author estepuma@hotmail.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appContext.xml"})
public class CertificadoUtilTest {

	private Logger logger = Logger.getLogger(CertificadoUtilTest.class);
	private static final String NO_CERTIFICADO_EXPECTED = "20001000000200001428";
	private static final String CERTIFICADO = "MIIEYTCCA0mgAwIBAgIUMjAwMDEwMDAwMDAyMDAwMDE0MjgwDQYJKoZIhvcNAQEFBQAwggFcMRowGAYDVQQDDBFBLkMuIDIgZGUgcHJ1ZWJhczEvMC0GA1UECgwmU2VydmljaW8gZGUgQWRtaW5pc3RyYWNpw7NuIFRyaWJ1dGFyaWExODA2BgNVBAsML0FkbWluaXN0cmFjacOzbiBkZSBTZWd1cmlkYWQgZGUgbGEgSW5mb3JtYWNpw7NuMSkwJwYJKoZIhvcNAQkBFhphc2lzbmV0QHBydWViYXMuc2F0LmdvYi5teDEmMCQGA1UECQwdQXYuIEhpZGFsZ28gNzcsIENvbC4gR3VlcnJlcm8xDjAMBgNVBBEMBTA2MzAwMQswCQYDVQQGEwJNWDEZMBcGA1UECAwQRGlzdHJpdG8gRmVkZXJhbDESMBAGA1UEBwwJQ295b2Fjw6FuMTQwMgYJKoZIhvcNAQkCDCVSZXNwb25zYWJsZTogQXJhY2VsaSBHYW5kYXJhIEJhdXRpc3RhMB4XDTEzMDUwNzE2MDEyOVoXDTE3MDUwNzE2MDEyOVowgdsxKTAnBgNVBAMTIEFDQ0VNIFNFUlZJQ0lPUyBFTVBSRVNBUklBTEVTIFNDMSkwJwYDVQQpEyBBQ0NFTSBTRVJWSUNJT1MgRU1QUkVTQVJJQUxFUyBTQzEpMCcGA1UEChMgQUNDRU0gU0VSVklDSU9TIEVNUFJFU0FSSUFMRVMgU0MxJTAjBgNVBC0THEFBQTAxMDEwMUFBQSAvIEhFR1Q3NjEwMDM0UzIxHjAcBgNVBAUTFSAvIEhFR1Q3NjEwMDNNREZOU1IwODERMA8GA1UECxMIcHJvZHVjdG8wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAKS/beUVy6E3aODaNuLd2S3PXaQre0tGxmYTeUxa55x2t/7919ttgOpKF6hPF5KvlYh4ztqQqP4yEV+HjH7yy/2d/+e7t+J61jTrbdLqT3WD0+s5fCL6JOrF4hqy//EGdfvYftdGRNrZH+dAjWWml2S/hrN9aUxraS5qqO1b7btlAgMBAAGjHTAbMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBBQUAA4IBAQACPXAWZX2DuKiZVv35RS1WFKgT2ubUO9C+byfZapV6ZzYNOiA4KmpkqHU/bkZHqKjR+R59hoYhVdn+ClUIliZf2ChHh8s0a0vBRNJ3IHfA1akWdzocYZLXjz3m0Er31BY+uS3qWUtPsONGVDyZL6IUBBUlFoecQhP9AO39er8zIbeU2b0MMBJxCt4vbDKFvT9i3V0Puoo+kmmkf15D2rBGR+drd8H8Yg8TDGFKf2zKmRsgT7nIeou6WpfYp570WIvLJQY+fsMp334D05Up5ykYSAxUGa30RdUzA4rxN5hT+W9whWVGD88TD33Nw55uNRUcRO3ZUVHmdWRG+GjhlfsD";
	private static final String PRIVATE_FILE_STRING = "MIICxjBABgkqhkiG9w0BBQ0wMzAbBgkqhkiG9w0BBQwwDgQIAgEAAoGBAKQCAgQAMBQGCCqGSIb3DQMHBAgwggJ2AgEAMASCAoD+W0qS7cu6pXUqVr3xMAvfnfTvmdYzPHjXh4IB45m2t66IShzPaQrNzj8qy1BEDaorR/sYMH4yPC+ejIfmP+qLuUCxsNaQSPLc9UYkpMP55LO5f3sq4CKcgoIMKVQchOfvUWzFz16RxjWIkgwZbsd5tHyjYMCMwZZTN05mxdAN+r9HFbGB9XAlElQylf4yWtft4TIBB6xOpiO+lKGXJzycstK+SfFpb7/R4LYaEG8V6ydGfqzDZUe/8oSI2RwO7RNzkWpUblXNuVucP7PcrtQWbq4/0AGwFilTTWTyAnG7xTuDGSuQGH/cs6TEHHP6E6GjO2Qba1VUmC93ZB6ectQC5utCJyQUvwjz3M9lLDuXnNOSwQCf/XUjpH8NFkQgMeoMXtDFsQ0n3J3f987OK3uWXtGMCF8S3Nh4OX0FUnG5zP7+dmS3EDjI4eXUxGmd2igFW8BfdPUbGLsd7K7H6IookHfP/mNsW8IHM7Igfl7EHQ+sbBFN5KHyWQRvLK5swQ8kE3FTY5ka0iR8vKuQ5/D3Zt/IvxY7bEFfhcoPmyN+ZPKcEyqpPUpsgXSFlipsqChrdNQZtLgBVTfs6S4jO6APA5EELQovUNzMGqXd2uQf8lzxypcsRSIwk9fZ0R+ER19DzMIzfr1Dp2FivbyDyrHj4I8gXsDffrW6VOjejv7D5c/rrON8bosOJiXKjXFrrT2GBdTZK/Sm7p6jAGQhlWLtdVmu5WpHMIXeIq1jrEuDM6VsUCMAPUezzvbAW9SZVdUbOnsQMJt7lZxAILOGwkBTFX5w2JsT9Dhbvggbf9d4KRDXGt7ynqVYin9W8+zesckHY2wEQo/TY4DhFkJJ7ap9";
	
	@Autowired
	private CertificadoUtil certificadoUtil;
	
	@Test
	public void testNoCertificado() {
		logger.info("*** Probando noCertificado con certificado de prueba ***");
		try {
			String noCertificado = certificadoUtil.getNoCertificado();
			
			logger.info("noCertificado: " + noCertificado);
			assertEquals(NO_CERTIFICADO_EXPECTED, noCertificado);
		} catch (FileNotFoundException | CertificateException e) {
			logger.error("Error en obtener el noCertificado.", e);
			fail("noCertificados no son iguales");
		}
		
	}
	
	@Test
	public void testCertificadoString() {
		logger.info("*** Probando certificado en string con certificado de prueba ***");
		String certificado = null;
		try {
			certificado = certificadoUtil.getCertificado();
			logger.info("certificado: " + certificado);
			assertEquals(CERTIFICADO, certificado);
		} catch (IOException e) {
			logger.error("Error al obtener el certificado");
			fail("Certificados no son iguales");
		}
			
	}
	
	@Test
	public void testPrivateFileString() {
		logger.info("*** Probando archivo de llave privada en cadena con archivo de prueba ***");
		String privateFileString = null;
		try {
			privateFileString = certificadoUtil.getPrivateFile();
			logger.info("certificado: " + privateFileString);
			assertEquals(PRIVATE_FILE_STRING, privateFileString);
		} catch (IOException e) {
			logger.error("Error al obtener el certificado");
			fail("Certificados no son iguales");
		}
			
	}

}
