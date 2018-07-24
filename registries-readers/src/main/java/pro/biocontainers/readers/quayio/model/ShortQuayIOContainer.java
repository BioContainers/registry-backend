package pro.biocontainers.readers.quayio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ShortQuayIOContainer {

    protected String kind;
    protected String name;
    protected Integer popularity;
    protected String namespace;
    protected Long last_modified;
    protected Boolean is_public;
    protected String description;

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
