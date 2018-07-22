package pro.biocontainers.readers.quayio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Stat {

    String date;
    Long count;
}
