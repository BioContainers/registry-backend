package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * A tool version describes a particular iteration of a tool as described
 * by a reference to a specific image and/or documents.
 */
@Data
public class ToolVersion {
    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

    @JsonProperty("id")
    private String id;

    @JsonProperty("image")
    private String image;

    @JsonProperty("registry_url")
    private String registryUrl;

    @JsonProperty("image_name")
    private String imageName;

    @JsonProperty("descriptor_type")
    private List<DescriptorType> descriptorType;

    @JsonProperty("containerfile")
    private Boolean containerfile;

    @JsonProperty("meta_version")
    private String metaVersion;

    @JsonProperty("verified")
    private Boolean verified;

    @JsonProperty("verified_source")
    private String verifiedSource;
}

