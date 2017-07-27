
                package mx.gob.sat.v33;
                import javax.xml.bind.annotation.XmlEnum;
                import javax.xml.bind.annotation.XmlType;

                @XmlType(name = "c_FormaPago", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
                @XmlEnum
                public enum CFormaPago {
C01("01"),C02("02"),C03("03"),C04("04"),C05("05"),C06("06"),C08("08"),C12("12"),C13("13"),C14("14"),C15("15"),C17("17"),C23("23"),C24("24"),C25("25"),C26("26"),C27("27"),C28("28"),C29("29"),C99("99");
private final String text;

    private CFormaPago(final String text) {
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