package pro.biocontainers.mongodb.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BioToolPublication {

    String pmcid;
    String pmid;
    String doi;
    PubMetadata pubmedMetadata;

}
