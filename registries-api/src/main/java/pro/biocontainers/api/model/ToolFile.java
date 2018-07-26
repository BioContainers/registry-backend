package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

/**
 * ToolFile
 */
@Data
public class ToolFile {
    @JsonProperty("path")
    private String path;

    @JsonProperty("file_type")
    private FileTypeEnum fileType;

    /**
     * Gets or Sets fileType
     */
    public enum FileTypeEnum {
        TEST_FILE("TEST_FILE"),

        PRIMARY_DESCRIPTOR("PRIMARY_DESCRIPTOR"),

        SECONDARY_DESCRIPTOR("SECONDARY_DESCRIPTOR"),

        CONTAINERFILE("CONTAINERFILE"),

        OTHER("OTHER");

        private String value;

        FileTypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static FileTypeEnum fromValue(String text) {
            for (FileTypeEnum b : FileTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }
}