package pro.biocontainers.readers.dockerhub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DockerHubTag {
    private String name;
    private Long full_size;
    private List<DockerHubImage> images;
    private Long id;
    private Long repository;
    private Long creator;
    private Long last_updater;
    private String last_updated;

}