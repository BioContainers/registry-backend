package pro.biocontainers.readers.biotools.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BioToolEntry {

    @JsonProperty("id")
    String id;

    @JsonProperty("name")
    String name;

    @JsonProperty("topic")
    Topic[] topics;

    @JsonProperty("description")
    String description;

    @JsonProperty("homepage")
    String homePage;

    @JsonProperty("download")
    DownloadURL[] downloadURLS;

    @JsonProperty("license")
    String license;

    @JsonProperty("contact")
    BioToolContact[] contacts;

    @JsonProperty("publication")
    BioToolPublication[] publications;



}
