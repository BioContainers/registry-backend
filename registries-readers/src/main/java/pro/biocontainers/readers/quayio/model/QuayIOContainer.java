package pro.biocontainers.readers.quayio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

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
}
