package pro.biocontainers.readers.quayio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortQuayIOContainer {

    @JsonProperty("kind")
    protected String kind;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("popularity")
    protected Integer popularity;

    @JsonProperty("namespace")
    protected String namespace;

    @JsonProperty("last_modified")
    protected Long last_modified;

    @JsonProperty("is_public")
    protected Boolean is_public;

    @JsonProperty("description")
    protected String description;

    public String getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public String getNamespace() {
        return namespace;
    }

    public Long getLast_modified() {
        return last_modified;
    }

    public Boolean getIs_public() {
        return is_public;
    }

    public String getDescription() {
        return description;
    }

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
