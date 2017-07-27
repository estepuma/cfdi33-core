
                package mx.gob.sat.v33;
                import javax.xml.bind.annotation.XmlEnum;
                import javax.xml.bind.annotation.XmlType;

                @XmlType(name = "c_PatenteAduanal", namespace = "http://www.sat.gob.mx/sitio_internet/cfd/catalogos")
                @XmlEnum
                public enum CPatenteAnual {
C0("0"),C1000("1000"),C1001("1001");
private final String text;

    private CPatenteAnual(final String text) {
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