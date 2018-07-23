package pro.biocontainers.readers.dockerhub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DockerHubTag {
    String name;
    Long full_size;
    List<DockerHubImage> images;
    Long id;
    Long repository;
    Long creator;
    Long last_updater;
    String last_updated;

}