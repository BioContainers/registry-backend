package pro.biocontainers.readers.dockerhub.model;

import lombok.Data;

import java.util.List;

@Data
public class DockerHubContainer {

    DockerHubContainerBriefInfo info;
    List<DockerHubTag> tags;

}
