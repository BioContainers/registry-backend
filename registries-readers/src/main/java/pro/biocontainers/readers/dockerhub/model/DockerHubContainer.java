package pro.biocontainers.readers.dockerhub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DockerHubContainer {

    private DockerHubContainerBriefInfo info;
    private List<DockerHubTag> tags;

}
