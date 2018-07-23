package pro.biocontainers.readers.dockerhub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DockerHubContainerBriefInfo {

    String user;
    String name;
    String namespace;
    String repository_type;
    Short status;
    String description;
    Boolean is_private;
    Boolean is_automated;
    Boolean can_edit;
    Long star_count;
    Long pull_count;
    String last_updated;
    Boolean has_starred;
//    String full_description;
}
