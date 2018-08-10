package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * A tool (or described tool) is defined as a tuple of a descriptor
 * file (which potentially consists of multiple files), a set of container images,
 * and a set of instructions for creating those images.
 *
 *
 */
@Data
@Builder
@ApiModel(description = "A tool (or described tool) is defined as a tuple of a descriptor file (which potentially consists of multiple files), a set of container images, and a set of instructions for creating those images.")
public class Tool {
    @ApiModelProperty(example = "http://agora.broadinstitute.org/tools/123456", required = true, value = "The URL for this tool in this registry")
    @JsonProperty("url")
    private String url;

    @ApiModelProperty(example = "123456", required = true, value = "A unique identifier of the tool, scoped to this registry")
    @JsonProperty("id")
    private String id;

    @ApiModelProperty(required = true, value = "The organization that published the image.")
    @JsonProperty("organization")
    private String organization;

    @ApiModelProperty(value = "The name of the tool.")
    @JsonProperty("toolname")
    private String toolname;

    @ApiModelProperty(required = true, value = "")
    @JsonProperty("toolclass")
    private ToolClass toolclass;

    @ApiModelProperty(value = "The description of the tool.")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(required = true, value = "Contact information for the author of this tool entry in the registry. (More complex authorship information is handled by the descriptor)")
    @JsonProperty("author")
    private String author;

    @ApiModelProperty(required = true, value = "The version of this tool in the registry. Iterates when fields like the description, author, etc. are updated.")
    @JsonProperty("meta_version")
    private String metaVersion;

    @ApiModelProperty(example = "https://bio.tools/tool/mytum.de/SNAP2/1", value = "An array of IDs for the applications that are stored inside this tool")
    @JsonProperty("contains")
    private List<String> contains;

    @ApiModelProperty(value = "Whether this tool has a checker tool associated with it")
    @JsonProperty("has_checker")
    private Boolean hasChecker;

    @ApiModelProperty(value = "Optional url to the checker tool that will exit successfully if this tool produced the expected result given test data.")
    @JsonProperty("checker_url")
    private String checkerUrl;

    @ApiModelProperty(value = "Reports whether this tool has been verified by a specific organization or individual")
    @JsonProperty("verified")
    private Boolean verified;

    @ApiModelProperty(value = "Source of metadata that can support a verified tool, such as an email or URL")
    @JsonProperty("verified_source")
    private String verifiedSource;

    @ApiModelProperty(value = "Reports whether this tool has been signed.")
    @JsonProperty("signed")
    private Boolean signed;

    @ApiModelProperty(required = true, value = "A list of versions for this tool")
    @JsonProperty("versions")
    private List<ToolVersion> versions = new ArrayList<>();

}

