package pro.biocontainers.readers.quayio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class QuayIOContainer extends ShortQuayIOContainer{

    Boolean is_organization;
    Boolean can_write;
    String  status_token;
    Boolean can_admin;
    Boolean trust_enabled;
    String  description;
    Map<String, Tag> tags;
    Long tag_expiration_s;
    Boolean is_starred;
    List<Stat> stats;

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
