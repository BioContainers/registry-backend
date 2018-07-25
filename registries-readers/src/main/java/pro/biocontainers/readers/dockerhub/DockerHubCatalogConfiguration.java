package pro.biocontainers.readers.dockerhub;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource(value = {"classpath:application.properties"})
public class DockerHubCatalogConfiguration {

    @Value("${elixir.docker.token}")
    private String accessToken;

    @Value("${elixir.docker.token.urls.containers-list}")
    private String containersListUrl;

    @Value("${elixir.docker.token.urls.container-details}")
    private String containersDetailsUrl;

    @Value("${elixir.docker.token.urls.container-tags}")
    private String containerTagsUrl;
}
