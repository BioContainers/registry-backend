package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * A tool (or described tool) is defined as a tuple of a descriptor file (which potentially consists of multiple files), a set of container images, and a set of instructions for creating those images.
 */
@Data
public class Tool {
    @JsonProperty("url")
    private String url;

    @JsonProperty("id")
    private String id;

    @JsonProperty("organization")
    private String organization;

    @JsonProperty("toolname")
    private String toolname;

    @JsonProperty("toolclass")
    private ToolClass toolclass;

    @JsonProperty("description")
    private String description;

    @JsonProperty("author")
    private String author;

    @JsonProperty("meta_version")
    private String metaVersion;

    @JsonProperty("contains")
    private List<String> contains;

    @JsonProperty("has_checker")
    private Boolean hasChecker;

    @JsonProperty("checker_url")
    private String checkerUrl;

    @JsonProperty("verified")
    private Boolean verified;

    @JsonProperty("verified_source")
    private String verifiedSource;

    @JsonProperty("signed")
    private Boolean signed;

    @JsonProperty("versions")
    private List<ToolVersion> versions = new ArrayList<>();

}

