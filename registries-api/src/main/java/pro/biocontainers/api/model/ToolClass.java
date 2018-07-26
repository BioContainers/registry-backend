package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Describes a class (type) of tool allowing us to categorize workflows, tasks, and maybe even other entities (such as services) separately
 */
@Data
public class ToolClass {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}

