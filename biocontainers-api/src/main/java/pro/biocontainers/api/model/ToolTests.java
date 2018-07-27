package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * A tool document that describes how to test with one or more sample test JSON.
 */
@Data
@ApiModel(description = "A tool document that describes how to test with one or more sample test JSON.")
public class ToolTests {
    @JsonProperty("test")
    @ApiModelProperty(value = "Optional test JSON content for this tool. (Note that one of test and URL are required)")
    private String test;

    @JsonProperty("url")
    @ApiModelProperty(value = "Optional url to the test JSON used to test this tool. Note that this URL should resolve to the raw unwrapped content that would otherwise be available in test.")
    private String url;
}

