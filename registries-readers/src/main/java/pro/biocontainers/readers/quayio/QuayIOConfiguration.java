package pro.biocontainers.readers.quayio;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class QuayIOConfiguration {

    @Value("${quay.io.token}")
    private String accessToken;

    @Value("${quay.io.urls.containers-list}")
    private String containersListUrl;

    @Value("${quay.io.urls.container-details}")
    private String containersDetailsUrl;

}
