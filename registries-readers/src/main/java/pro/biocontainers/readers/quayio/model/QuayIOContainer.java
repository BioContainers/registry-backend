package pro.biocontainers.readers.quayio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class QuayIOContainer extends ShortQuayIOContainer {

    private Boolean is_organization;
    private Boolean can_write;
    private String status_token;
    private Boolean can_admin;
    private Boolean trust_enabled;
    private String description;
    private Map<String, Tag> tags;
    private Long tag_expiration_s;
    private Boolean is_starred;
    private List<Stat> stats;

    @Override
    public String toString() {
        return "QuayIOContainer{" +
                "is_organization=" + is_organization +
                ", can_write=" + can_write +
                ", status_token='" + status_token + '\'' +
                ", can_admin=" + can_admin +
                ", trust_enabled=" + trust_enabled +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", tag_expiration_s=" + tag_expiration_s +
                ", is_starred=" + is_starred +
                ", stats=" + stats +
                ", kind='" + kind + '\'' +
                ", name='" + name + '\'' +
                ", popularity=" + popularity +
                ", namespace='" + namespace + '\'' +
                ", last_modified=" + last_modified +
                ", is_public=" + is_public +
                ", description='" + description + '\'' +
                '}';
    }
}
