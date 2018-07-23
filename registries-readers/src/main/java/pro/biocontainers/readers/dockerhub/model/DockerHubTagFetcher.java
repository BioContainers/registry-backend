package pro.biocontainers.readers.dockerhub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DockerHubTagFetcher {

    Long count;
    String next;
    String previuos;

    @JsonProperty(value = "results")
    List<DockerHubTag> tags;

}
