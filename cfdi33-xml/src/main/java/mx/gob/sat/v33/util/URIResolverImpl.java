package mx.gob.sat.v33.util;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

public class URIResolverImpl implements URIResolver {

	@Override
	public Source resolve(String href, String base) throws TransformerException {
		return new StreamSource(getClass().getResourceAsStream(href));
	}

}
