package pro.biocontainers.readers.quayio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ShortQuayIOContainer {

    String kind;

    String name;

    Integer popularity;

    String namespace;

    Long last_modified;

    Boolean is_public;

    String description;

    @Override
    public String toString() {
        return "ShortQuayIOContainer{" +
                "kind='" + kind + '\'' +
                ", name='" + name + '\'' +
                ", popularity=" + popularity +
                ", namespace='" + namespace + '\'' +
                ", last_modified=" + last_modified +
                ", is_public=" + is_public +
                ", description='" + description + '\'' +
                '}';
    }
}
