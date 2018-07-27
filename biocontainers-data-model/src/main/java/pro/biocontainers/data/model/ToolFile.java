package pro.biocontainers.data.model;


/**
 * ToolFile
 */

public class ToolFile {

    /** Path **/
    public String getPath;

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
        public String toString() {
            return String.valueOf(value);
        }


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