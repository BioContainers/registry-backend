package pro.biocontainers.readers.biotools.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class PubMetadata {

    @JsonProperty("title")
    String title;

    @JsonProperty("abstract")
    String pubAbstract;

    @JsonProperty("date")
    String date;

    @JsonProperty("citationCount")
    Integer citationCount;

    @JsonProperty("authors")
    PubAuthor[] pubAuthors;

    @JsonProperty("journal")
    String journal;

//    public List<String> getAuthors() {
//        if(pubAuthors != null && pubAuthors.length > 0)
//            return Arrays.asList(pubAuthors).stream().map(PubAuthor::getName).collect(Collectors.toList());
//        return Collections.EMPTY_LIST;
//    }
}
