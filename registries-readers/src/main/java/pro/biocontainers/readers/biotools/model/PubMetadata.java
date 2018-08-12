package pro.biocontainers.readers.biotools.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class PubMetadata {

    @JsonProperty("title")
    String title;

    @JsonProperty("abstract")
    String pubAbstract;

    @JsonProperty("date")
    String date;

    @JsonProperty("citationCount")
    Integer citationCount;

    @JsonProperty("authors")
    String[] authors;

    @JsonProperty("journal")
    String journal;
}
