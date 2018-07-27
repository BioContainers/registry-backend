package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Describes a class (type) of tool allowing us to categorize workflows, tasks, and maybe even other entities (such as services) separately
 */
@Data
@ApiModel(description = "Describes a class (type) of tool allowing us to categorize workflows, tasks, and maybe even other entities (such as services) separately")
public class ToolClass {
    @ApiModelProperty(value = "The unique identifier for the class")
    @JsonProperty("id")
    private String id;

    @ApiModelProperty(value = "A short friendly name for the class")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "A longer explanation of what this class is and what it can accomplish")
    @JsonProperty("description")
    private String description;
}

