package pro.biocontainers.readers.biotools.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BioToolPublication {

    @JsonProperty("pmcid")
    String pmcid;

    @JsonProperty("pmid")
    String pmid;

    @JsonProperty("doi")
    String doi;

    @JsonProperty("metadata")
    PubMetadata pubmedMetadata;

}
