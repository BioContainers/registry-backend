package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * A tool document that describes how to test with one or more sample test JSON.
 */
@Data
public class ToolTests {
    @JsonProperty("test")
    private String test;

    @JsonProperty("url")
    private String url;
}

