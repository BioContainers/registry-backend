package pro.biocontainers.readers.biotools.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BioToolContact {

    @JsonProperty("name")
    String name;

    @JsonProperty("url")
    String url;

    @JsonProperty("email")
    String email;
}