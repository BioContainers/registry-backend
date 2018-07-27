package pro.biocontainers.data.model;

/**
 * The type of descriptor that represents this version of the
 * tool (e.g. CWL, WDL, or NFL).
 */
public enum DescriptorType {

    CWL("CWL"),

    WDL("WDL"),

    GALAXY("GALAXY"),

    NFL("NFL");

    private String value;

    DescriptorType(String value) {
        this.value = value;
    }

    @Override

    public String toString() {
        return String.valueOf(value);
    }

    public static DescriptorType fromValue(String text) {
        for (DescriptorType b : DescriptorType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

