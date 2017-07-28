package mx.gob.sat.v33;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sat.v33.Comprobante.Emisor;
import mx.gob.sat.v33.binding.DateTimeCustomBinder;
import mx.gob.sat.v33.util.CadenaOriginal;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appContext.xml"})
public class ComprobanteTest {
	private Logger logger = Logger.getLogger(ComprobanteTest.class);
	
	private final static String CADENA_ORIGINAL_ESPERADA = "||3.3|1|2017-07-28T15:57:17|20001000000200001428|100.23|MXN|100.24|I|14080|AAA010101AAA|Emisor de prueba|601||";
	private final static String SELLO_ESPERADO = "Dm3v4OydicaSE0n5LwsT+DR4He/HEueE7MUyCzQB6sJ8IjYkNhliVXoY32khTqjVv5Eu/1EU8zR+KWNC704Unbya0/c1iwqlk6Vv+mA8yseSDj4wU6bfGwOo1Q3+S6ZtkhWQF0VVWXQHhRmnhwrv6DiULJKpBGYivB8IO43YgLk=";
	
	@Autowired
	private CadenaOriginal cadenaOriginal;
	
	@Value("${cert.file.string}")
	private String certFileString;
	
	@Value("${cert.no.certificado}")
	private String noCertificado;
	
	@Test
	public void test() {
		
		ObjectFactory of = new ObjectFactory();
		DateTimeCustomBinder dateTimeCustomBinder = new DateTimeCustomBinder();
		
//		Datos generales del CFDI
		Comprobante cfdi33 = of.createComprobante();
		cfdi33.setVersion(CVersion.V33.getValue());
		cfdi33.setFolio("1");
		cfdi33.setFecha(dateTimeCustomBinder.parseDateTime("2017-07-28T15:57:17"));
		cfdi33.setNoCertificado(noCertificado);
		cfdi33.setCertificado(certFileString);
		cfdi33.setSubTotal(new BigDecimal(100.234).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		cfdi33.setMoneda(CMoneda.MXN);
		cfdi33.setTotal(new BigDecimal(100.236).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		cfdi33.setTipoDeComprobante(CTipoDeComprobante.I);
		cfdi33.setLugarExpedicion("14080");
		
//		Emisor
		Emisor emisor = of.createComprobanteEmisor();
		emisor.setNombre("Emisor de prueba");
		emisor.setRfc("AAA010101AAA");
		emisor.setRegimenFiscal(CRegimenFiscal.valueOf("C" + "601"));
		cfdi33.setEmisor(emisor);
		
		logger.info(cfdi33.toString());
		
//		**** Se obtiene cadena original ****
		byte[] cadOrigBytes = null;
		try {
			cadOrigBytes = cadenaOriginal.getCadenaOriginal33(cfdi33);
			String cadOrigEncod = new String(cadOrigBytes,"UTF-8");
			
			logger.info("Cadena Original:" + cadOrigEncod);
			
			assertEquals(CADENA_ORIGINAL_ESPERADA, cadOrigEncod);
			
		} catch (UnsupportedEncodingException | JAXBException | TransformerException e1) {
			logger.error("Error en obtener cadena original", e1);
			fail("Cadenas originales no son iguales");
		}
		
//		**** Se firma la cadena original ****
		try {
			String firma = cadenaOriginal.firmarCadenaOriginal(cadOrigBytes);
			
			logger.info("Firma:" + firma);
			
			assertEquals(SELLO_ESPERADO, firma);
			
			cfdi33.setSello(firma);
		} catch (Exception e) {
			logger.error("Error en firmar cadena original", e);
			fail("Sellos no son iguales");
		}
		
	}

}
