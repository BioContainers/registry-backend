package pro.biocontainers.readers;

import java.util.Optional;

public enum ExternalID {
    PUBMED("pubmed"),
    BIOTOOLS("biotools"),
    DOI("doi"),
    BIOCONDUCTOR("bioconductor");

    String name;

    ExternalID(String value){
        this.name = value;
    }

    public static Optional<ExternalID> findValue(String value){
        if(value.equalsIgnoreCase("pubmed"))
            return Optional.of(PUBMED);
        if(value.equalsIgnoreCase("biotools"))
            return Optional.of(BIOTOOLS);
        if(value.equalsIgnoreCase("doi"))
            return Optional.of(DOI);
        if(value.equalsIgnoreCase("bioconductor"))
            return Optional.of(BIOCONDUCTOR);
        return Optional.empty();
    }
}
