package mx.gob.sat.v33;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.gob.sat.v33.Comprobante.Conceptos;
import mx.gob.sat.v33.Comprobante.Conceptos.Concepto;
import mx.gob.sat.v33.Comprobante.Conceptos.Concepto.Impuestos;
import mx.gob.sat.v33.Comprobante.Conceptos.Concepto.Impuestos.Traslados;
import mx.gob.sat.v33.Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado;
import mx.gob.sat.v33.Comprobante.Emisor;
import mx.gob.sat.v33.Comprobante.Receptor;
import mx.gob.sat.v33.binding.DateTimeCustomBinder;
import mx.gob.sat.v33.util.CadenaOriginal;
import mx.gob.sat.v33.util.TransformerJava2XML;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/appContext.xml" })
public class ComprobanteTest {
	private Logger logger = Logger.getLogger(ComprobanteTest.class);

	private final static String CADENA_ORIGINAL_ESPERADA = "||3.3|1|2017-08-01T15:57:17|30001000000300023685|100.23|MXN|100.24|I|14080|AAA010101AAA|PRUEBAS INTEGRACION S.A. DE C.V.|601|BBB010101BBB|CLIENTE PRUEBA S.A. DE C.V.|MEX|D01|01010101|sku001|1.00|KGM|Kilo|Prueba concepto1|123.38|123.38|0.356763|002|Tasa|16.0||";
	private final static String SELLO_ESPERADO = "LBpKcIOk92WIcfDUGDtIPNY3yYj4v/ctFRN3HJyCxBkRToAC1+l2DJDaui3yhmTO8a9yqKHX1eaceYsjRZ+sxzvyUnyAG4chzaRdp0mx1ROCpabDb00VuEyXJonWcGTNtXa/AXaVdqZmUaGH9uUhHkN96dYXpxY+mLgud4qB5bfBYHHaEXVWaw0DL/+ckGNkfdFkVinwyg7TjcyPpWThgNMeh2bP+J/T8DRHr+NvuaTEfLtGmRh0OzIa54iLcOeY29Sx/qyl46MMCEroFagj387yh+QNIxL6gvCfu2q45b2+qRs0ubGA5drOVeQPbeJtO6uX/pyH1LXqoIgXuCIQcw==";

	@Autowired
	private CadenaOriginal cadenaOriginal;

	@Autowired
	private TransformerJava2XML transformerJava2XML;

	@Value("${cert.file.string}")
	private String certFileString;

	@Value("${cert.no.certificado}")
	private String noCertificado;
	
	@Test
	public void comprobanteBasicoTest() {
		long time = System.currentTimeMillis();

		ObjectFactory of = new ObjectFactory();
		DateTimeCustomBinder dateTimeCustomBinder = new DateTimeCustomBinder();

		// Datos generales del CFDI
		Comprobante cfdi33 = of.createComprobante();
		cfdi33.setVersion(CVersion.V33.getValue());
		cfdi33.setFolio("1");
		cfdi33.setFecha(dateTimeCustomBinder.parseDateTime("2017-08-01T15:57:17"));
		cfdi33.setNoCertificado(noCertificado);
		cfdi33.setCertificado(certFileString);
		cfdi33.setSubTotal(new BigDecimal(100.234).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		cfdi33.setMoneda(CMoneda.MXN);
		cfdi33.setTotal(new BigDecimal(100.236).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		cfdi33.setTipoDeComprobante(CTipoDeComprobante.I);
		cfdi33.setLugarExpedicion("14080");

		// Emisor
		Emisor emisor = of.createComprobanteEmisor();
		emisor.setNombre("PRUEBAS INTEGRACION S.A. DE C.V.");
		emisor.setRfc("AAA010101AAA");
		emisor.setRegimenFiscal(CRegimenFiscal.valueOf("C" + "601"));
		cfdi33.setEmisor(emisor);

		// Receptor
		Receptor receptor = of.createComprobanteReceptor();
		receptor.setNombre("CLIENTE PRUEBA S.A. DE C.V.");
		receptor.setRfc("BBB010101BBB");
		receptor.setUsoCFDI(CUsoCFDI.D_01);
		receptor.setResidenciaFiscal(CPais.MEX);
		cfdi33.setReceptor(receptor);

		// Conceptos
		Conceptos conceptos = of.createComprobanteConceptos();

		Concepto concepto1 = of.createComprobanteConceptosConcepto();
		concepto1.setClaveProdServ("01010101"); // TODO Debe ser catalogo
		concepto1.setNoIdentificacion("sku001");
		concepto1.setCantidad(new BigDecimal(1.0).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		concepto1.setClaveUnidad(CClaveUnidad.CKGM); // TODO el catalogo no tres el valor correcto, en su lugar trae la
														// cadena CKGM y debe ser KGM
		concepto1.setUnidad("Kilo");
		concepto1.setDescripcion("Prueba concepto1");
		concepto1.setValorUnitario(new BigDecimal(123.375).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		concepto1.setImporte(new BigDecimal(123.375).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		conceptos.getConcepto().add(concepto1);

		// Impuestos
		Impuestos impuestos = of.createComprobanteConceptosConceptoImpuestos();

		Traslados traslados = of.createComprobanteConceptosConceptoImpuestosTraslados();

		Traslado traslado = of.createComprobanteConceptosConceptoImpuestosTrasladosTraslado();
		traslado.setBase(new BigDecimal(0.356763).setScale(6, BigDecimal.ROUND_HALF_EVEN));
		traslado.setImporte(new BigDecimal(16).setScale(1, BigDecimal.ROUND_HALF_EVEN));
		traslado.setImpuesto(CImpuesto.C002.getValue());
		traslado.setTipoFactor(CTipoFactor.TASA);
		traslados.getTraslado().add(traslado);

		impuestos.setTraslados(traslados);
		concepto1.setImpuestos(impuestos);
		cfdi33.setConceptos(conceptos);

		logger.info(cfdi33.toString());

		// **** Se obtiene cadena original ****
		byte[] cadOrigBytes = null;
		try {
			cadOrigBytes = cadenaOriginal.getCadenaOriginal33(cfdi33);
			String cadOrigEncod = new String(cadOrigBytes, "UTF-8");

			logger.info("Cadena Original:" + cadOrigEncod);

			assertEquals(CADENA_ORIGINAL_ESPERADA, cadOrigEncod);

		} catch (UnsupportedEncodingException | JAXBException | TransformerException e1) {
			logger.error("Error en obtener cadena original", e1);
			fail("Cadenas originales no son iguales");
		}

		// **** Se firma la cadena original ****
		try {
			String firma = cadenaOriginal.firmarCadenaOriginal(cadOrigBytes);

			logger.info("Firma:" + firma);

			assertEquals(SELLO_ESPERADO, firma);

			cfdi33.setSello(firma);
		} catch (Exception e) {
			logger.error("Error en firmar cadena original", e);
			fail("Sellos no son iguales");
		}

		// **** Transformar de java a XML ****
		byte[] xmlCFDI = null;
		try {
			xmlCFDI = transformerJava2XML.java2XMLVersion3_3BytesArr(cfdi33);
			logger.info("** XML:" + new String(xmlCFDI));
		} catch (Exception e) {
			logger.error("Error al convertir Java a XML", e);
		}

		logger.debug(
				"** Tiempo de generacion del CFDI - creacion del comprobante, cadena original, sello y generacion de xml - "
						+ DurationFormatUtils.formatDuration(System.currentTimeMillis() - time, "HH:mm:ss:SS"));
		
	}
	

}
