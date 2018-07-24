package pro.biocontainers.readers.dockerhub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DockerHubContainerBriefInfo {

    private String user;
    private String name;
    private String namespace;
    private String repository_type;
    private Short status;
    private String description;
    private Boolean is_private;
    private Boolean is_automated;
    private Boolean can_edit;
    private Long star_count;
    private Long pull_count;
    private String last_updated;
    private Boolean has_starred;
//    private String full_description;
}
