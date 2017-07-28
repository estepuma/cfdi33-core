package mx.gob.sat.v33.binding;

import mx.gob.sat.v33.CRegimenFiscal;

public class RegimenFiscalCustomBinding {
	public static CRegimenFiscal parseRegimenFiscal(String regFiscal) {
		return CRegimenFiscal.valueOf(regFiscal);
	}
	  
	public static String printRegimenFiscal(CRegimenFiscal regFiscal) {
		return regFiscal.getValue();
	}
}
