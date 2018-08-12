package pro.biocontainers.mongodb.model;


import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PubMetadata {

    String title;
    String pubAbstract;
    String date;
    Integer citationCount;
    List<String> pubAuthors;
    String journal;
}
