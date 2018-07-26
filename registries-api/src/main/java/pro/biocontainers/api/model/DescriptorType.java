package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The type of descriptor that represents this version of the tool (e.g. CWL, WDL, or NFL).
 */
public enum DescriptorType {

    CWL("CWL"),

    WDL("WDL"),

    NFL("NFL");

    private String value;

    DescriptorType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static DescriptorType fromValue(String text) {
        for (DescriptorType b : DescriptorType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

