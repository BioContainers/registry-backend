package pro.biocontainers.readers.dockerhub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DockerHubImage {
    private Long size;
    private String architecture;
    private String os;
    private String os_version;
}