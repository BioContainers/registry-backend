package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Describes this registry to better allow for mirroring and indexing.
 */
@Data
@Builder
@ApiModel(description = "Describes this registry to better allow for mirroring and indexing.")
public class Metadata {

    @ApiModelProperty(required = true, value = "The version of this registry")
    @JsonProperty("version")
    private String version;

    @ApiModelProperty(required = true, value = "The version of the GA4GH tool-registry API supported by this registry")
    @JsonProperty("api_version")
    private String apiVersion;

    @ApiModelProperty(value = "A country code for the registry (ISO 3166-1 alpha-3)")
    @JsonProperty("country")
    private String country;

    @ApiModelProperty(value = "A friendly name that can be used in addition to the hostname to describe a registry")
    @JsonProperty("friendly_name")
    private String friendlyName;

    @ApiModelProperty(value = "Brief descrption")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(value = "Contact details")
    @JsonProperty("contact")
    private Contact contact;

    @ApiModelProperty(value = "license")
    @JsonProperty("license")
    private String license;
}

