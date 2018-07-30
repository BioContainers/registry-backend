package pro.biocontainers.readers.quayio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ListShortContainers {

    @JsonProperty(value = "repositories")
    private ShortQuayIOContainer[] repositories;

    public List<ShortQuayIOContainer> getShortContainersList(){
        return Arrays.asList(repositories);
    }
}
