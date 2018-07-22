package pro.biocontainers.readers.quayio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Tag {
    String image_id;
    String last_modified;
    String name;
    String manifest_digest;
    Long size;
}
