package pro.biocontainers.mongodb.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Builder
@Data
@Document(collection = "biocontainers")
public class BioContainer {

    /** Native Identifier from MongoDB */
    @Id
    @Indexed(name = "id")
    ObjectId id;

    /** Accession provided by BioContainers Community**/
    @Indexed(name = "accession", unique = true)
    String accession;

    @Indexed(name = "namespace")
    String nameSpace;

    @Indexed(name = "name")
    String name;

    /** Main URL where the user can download the container. **/
    @Indexed(name = "url")
    String url;

    @Field(value = "description")
    String description;

    @Indexed(name = "pullCount")
    Integer pullCount;

    @Indexed(name = "lastUpdate")
    Date lastUpdate;

    @Indexed(name = "starred")
    Boolean starred;

    @Indexed(name = "tags")
    List<Tuple<String, Integer>> tags;

    @Indexed(name = "version")
    String version;

}
