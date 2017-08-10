/**
 * 
 */
package mx.gob.sat.v33.util;

import java.util.Map;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * @author estepuma@hotmail.com
 *
 */
public class NamespacePrefixMapperImpl extends NamespacePrefixMapper{
	private final Map<String, String> map;
	
	public NamespacePrefixMapperImpl(Map<String, String> map) {
		this.map = map;
	}
	
	public String getPreferredPrefix(String namespaceUri, String suggestion, 
			                                   boolean requirePrefix) {
	    String value = map.get(namespaceUri);
	    return (value != null) ? value : suggestion;
    }
	    
	public String[] getPreDeclaredNamespaceUris() {
		return new String[0];
	}
}
