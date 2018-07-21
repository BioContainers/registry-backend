package pro.biocontainers.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;
import java.util.Set;


@Document(collection = "biocontainers")
@Builder
@Data
public class BioContainer {

    /** Native Identifier from MongoDB */
    @Id
    @Indexed(name = "id")
    ObjectId id;

    /** Accession provided by BioContainers Community**/
    @Indexed(name = "accession", unique = true)
    String accession;

    /** URLs where the containers can be download, The Key of the hash is the URL
     * the value is the status of the URL **/
    @Indexed(name = "urls", unique = true)
    Map<String, String> urls;

    /** Main URL where the user can download the container. **/
    @Indexed(name = "mainURL", unique = true)
    String url;

    @Indexed(name = "name")
    String name;

    @Indexed(name = "description")
    String description;

    @Field("toolIds")
    Set<String> toolIds;

    @Indexed(name= "about.home")
    Set<String> softwareURL;

}
