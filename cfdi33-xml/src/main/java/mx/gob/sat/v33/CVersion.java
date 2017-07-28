package mx.gob.sat.v33;

public enum CVersion {
	V33("3.3");
	
	private final String text;

    private CVersion(final String text) {
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
