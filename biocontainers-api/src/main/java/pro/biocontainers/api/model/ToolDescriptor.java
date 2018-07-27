package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * A tool descriptor is a metadata document that describes one or more tools.
 */
@Data
@ApiModel(description = "A tool descriptor is a metadata document that describes one or more tools.")
public class ToolDescriptor {
    @JsonProperty("type")
    @ApiModelProperty(required = true, value = "")
    private DescriptorType type;

    @JsonProperty("descriptor")
    @ApiModelProperty(value = "The descriptor that represents this version of the tool.")
    private String descriptor;

    @JsonProperty("url")
    @ApiModelProperty(example = "https://raw.githubusercontent.com/ICGC-TCGA-PanCancer/pcawg_delly_workflow/ea2a5db69bd20a42976838790bc29294df3af02b/delly_docker/Delly.cwl", value = "Optional url to the underlying tool descriptor, should include version information, and can include a git hash")
    private String url;
}

