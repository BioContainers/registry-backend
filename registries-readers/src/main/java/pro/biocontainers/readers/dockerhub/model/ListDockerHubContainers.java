package pro.biocontainers.readers.dockerhub.model;


import lombok.Data;

import java.util.List;

@Data
public class ListDockerHubContainers {

    List<DockerHubContainerBriefInfo> repositories;

}
