package mx.gob.sat.v33.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import mx.gob.sat.v33.CRegimenFiscal;

public class RegimenFiscalAdapter extends XmlAdapter<String, CRegimenFiscal>{

	@Override
	public String marshal(CRegimenFiscal regFiscal) throws Exception {
		return regFiscal.getValue();
	}

	@Override
	public CRegimenFiscal unmarshal(String regFiscal) throws Exception {
		if(regFiscal != null) {
			return CRegimenFiscal.valueOf(regFiscal);
		} else {
			return null;
		}
		
	}

}
