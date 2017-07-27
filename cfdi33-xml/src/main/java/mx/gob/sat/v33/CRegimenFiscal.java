
                package mx.gob.sat.v33;
                import javax.xml.bind.annotation.XmlEnum;
                import javax.xml.bind.annotation.XmlType;

                @XmlType(name = "c_RegimenFiscal", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
                @XmlEnum
                public enum CRegimenFiscal {
C601("601"),C603("603"),C605("605"),C606("606"),C608("608"),C609("609"),C610("610"),C611("611"),C612("612"),C614("614"),C616("616"),C620("620"),C621("621"),C622("622"),C623("623"),C624("624"),C628("628"),C607("607"),C629("629"),C630("630"),C615("615");
private final String text;

    private CRegimenFiscal(final String text) {
        this.text = text;
    }

    public String getValue() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
    }