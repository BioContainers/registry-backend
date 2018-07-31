package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * A tool version describes a particular iteration of a tool as described
 * by a reference to a specific image and/or documents.
 */
@Data
@Builder
@ApiModel(description = "A tool version describes a particular iteration of a tool as described by a reference to a specific image and/or documents.")
public class ToolVersion {
    @JsonProperty("name")
    @ApiModelProperty(value = "The name of the version.")
    private String name;

    @JsonProperty("url")
    @ApiModelProperty(example = "http://agora.broadinstitute.org/tools/123456/1", required = true, value = "The URL for this tool in this registry")
    private String url;

    @JsonProperty("id")
    @ApiModelProperty(example = "v1", required = true, value = "An identifier of the version of this tool for this particular tool registry")
    private String id;

    @JsonProperty("latest_image")
    @ApiModelProperty(example = "quay.io/seqware/seqware_full/1.1", value = "The docker path to the image (and version) for this tool")
    private String latestImage;

    @JsonProperty("images")
    @ApiModelProperty(example = "{1.1--python, 1.1--deb}", value = "The docker path to the image (and version) for this tool")
    private List<String> images;


    @JsonProperty("registry_url")
    @ApiModelProperty(value = "A URL to a Singularity registry is provided when a specific type of image does not use ids in the Docker format. Used along with image_name to locate a specific image.")
    private String registryUrl;

    @JsonProperty("image_name")
    @ApiModelProperty(value = "Used in conjunction with a registry_url if provided to locate images")
    private String imageName;

    @JsonProperty("descriptor_type")
    @ApiModelProperty(value = "The type (or types) of descriptors available.")
    private List<DescriptorType> descriptorType;

    @JsonProperty("containerfile")
    @ApiModelProperty(value = "Reports if this tool has a containerfile available.")
    private Boolean containerfile;

    @JsonProperty("meta_version")
    @ApiModelProperty(required = true, value = "The version of this tool version in the registry. Iterates when fields like the description, author, etc. are updated.")
    private String metaVersion;

    @JsonProperty("verified")
    @ApiModelProperty(value = "Reports whether this tool has been verified by a specific organization or individual")
    private Boolean verified;

    @JsonProperty("verified_source")
    @ApiModelProperty(value = "Source of metadata that can support a verified tool, such as an email or URL")
    private String verifiedSource;
}

