package pro.biocontainers.readers.biotools.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class DownloadURL {

    @JsonProperty("url")
    String url;

    @JsonProperty("type")
    String type;


}
