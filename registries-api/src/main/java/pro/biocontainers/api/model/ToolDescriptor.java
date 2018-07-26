package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * A tool descriptor is a metadata document that describes one or more tools.
 */
@Data
public class ToolDescriptor {
    @JsonProperty("type")
    private DescriptorType type;

    @JsonProperty("descriptor")
    private String descriptor;

    @JsonProperty("url")
    private String url;
}

