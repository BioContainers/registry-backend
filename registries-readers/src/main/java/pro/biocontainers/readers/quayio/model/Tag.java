package pro.biocontainers.readers.quayio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Tag {
    private String image_id;
    private String last_modified;
    private String name;
    private String manifest_digest;
    private Long size;
}
