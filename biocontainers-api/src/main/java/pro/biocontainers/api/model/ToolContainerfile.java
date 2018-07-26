package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * A containerfile is a document that describes how to build a particular container image.
 * Examples include Dockerfiles for creating Docker images and Singularity recipes for Singularity images
 */
@Data
public class ToolContainerfile {
    @JsonProperty("containerfile")
    private String containerfile;

    @JsonProperty("url")
    private String url;
}

