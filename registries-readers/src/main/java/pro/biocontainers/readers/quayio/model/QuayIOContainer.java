package pro.biocontainers.readers.quayio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pro.biocontainers.readers.IRegistryContainer;
import pro.biocontainers.readers.Tuple;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class QuayIOContainer extends ShortQuayIOContainer implements IRegistryContainer {

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

    @Override
    public String getNameSpace() {
        return this.namespace;
    }

    @Override
    public Boolean isPrivate() {
        return false;
    }

    @Override
    public Integer getPullCount() {
        return this.popularity;
    }

    @Override
    public Date getLastUpdated() {
        if(this.last_modified != null)
            return new Date(this.last_modified * 1000);
        return null;
    }

    @Override
    public Boolean isStarred() {
        return is_starred;
    }

    @Override
    public Integer getStartCount() {
        return is_starred?1:0 ;
    }

    @Override
    public List<Tuple<String, Integer>> getContainerTags() {
        return tags.entrySet().stream().map(x -> new Tuple<>(x.getValue().getName(), x.getValue().getSize().intValue())).collect(Collectors.toList());

    }

    @Override
    public List<Tuple<Date, Integer>> getContainerStats() {
        return stats.stream().map( x-> new Tuple<>(new Date(x.getDate()), x.getCount().intValue())).collect(Collectors.toList());
    }
}
