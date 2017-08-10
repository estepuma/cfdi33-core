/**
 * 
 */
package mx.gob.sat.v33.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import mx.gob.sat.v33.CClaveUnidad;

/**
 * @author estepuma@hotmail.com
 *
 */
public class CClaveUnidadAdapter extends XmlAdapter<String, CClaveUnidad>{

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public String marshal(CClaveUnidad cclaveUnidad) throws Exception {
		return cclaveUnidad.getValue();
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public CClaveUnidad unmarshal(String cclaveUnidad) throws Exception {
		if(cclaveUnidad != null) {
			return CClaveUnidad.valueOf(cclaveUnidad);
		} else {
			return null;
		}
	}

}
