package pro.biocontainers.mongodb.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DownloadURL {
    String url;
    String type;
}
