
                package mx.gob.sat.v33;
                import javax.xml.bind.annotation.XmlEnum;
                import javax.xml.bind.annotation.XmlType;

                @XmlType(name = "c_TipoRelacion", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
                @XmlEnum
                public enum CTipoRelacion {
C01("01"),C02("02"),C03("03"),C04("04"),C05("05"),C06("06");
private final String text;

    private CTipoRelacion(final String text) {
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