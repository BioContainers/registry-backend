package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * Error
 */
@Data
public class Error {
    @JsonProperty("code")
    private Integer code = 500;

    @JsonProperty("message")
    private String message = "Internal Server Error";
}

