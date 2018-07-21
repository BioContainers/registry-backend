package pro.biocontainers.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;


@Document(collection = "biocontainers")
@Builder
@Data
public class BioContainer {

    @Id
    @Indexed(name = "id")
    ObjectId id;

    @Indexed(name = "accession")
    String accession;

    @Indexed(name = "url")
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
