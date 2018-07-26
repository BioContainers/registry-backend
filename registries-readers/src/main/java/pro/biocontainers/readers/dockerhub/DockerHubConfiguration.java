package pro.biocontainers.readers.dockerhub;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource(value = {"classpath:application.properties"})
public class DockerHubConfiguration {

    @Value("${dockerhub.token}")
    private String accessToken;

    @Value("${dockerhub.urls.containers-list}")
    private String containersListUrl;

    @Value("${dockerhub.urls.container-details}")
    private String containersDetailsUrl;

    @Value("${dockerhub.urls.container-tags}")
    private String containerTagsUrl;
}
