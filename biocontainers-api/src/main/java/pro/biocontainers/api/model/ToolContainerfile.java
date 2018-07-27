package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * A containerfile is a document that describes how to build a particular container image.
 * Examples include Dockerfiles for creating Docker images and Singularity recipes for Singularity images
 */
@ApiModel(description = "A containerfile is a document that describes how to build a particular container image. Examples include Dockerfiles for creating Docker images and Singularity recipes for Singularity images")
@Data
public class ToolContainerfile {
    @ApiModelProperty(required = true, value = "The container specification for this tool.")
    @JsonProperty("containerfile")
    private String containerfile;

    @ApiModelProperty(example = "https://raw.githubusercontent.com/ICGC-TCGA-PanCancer/pcawg_delly_workflow/c83478829802b4d36374870843821abe1b625a71/delly_docker/Dockerfile", value = "Optional url to the file used to build this image, should include version information, and can include a git hash")
    @JsonProperty("url")
    private String url;
}

