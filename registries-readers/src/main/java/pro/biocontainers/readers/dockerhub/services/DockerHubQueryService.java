package pro.biocontainers.readers.dockerhub.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.biocontainers.readers.dockerhub.DockerHubConfiguration;
import pro.biocontainers.readers.dockerhub.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class DockerHubQueryService {

    private RestTemplate restTemplate;

    private DockerHubConfiguration configuration;

    public DockerHubQueryService(RestTemplateBuilder builder, DockerHubConfiguration configuration) {
        restTemplate = builder.build();
        this.configuration = configuration;
        String accessToken = configuration.getAccessToken();
        if (accessToken != null) {
            this.restTemplate.getInterceptors()
                    .add(getAccessTokenInterceptor(accessToken));
        } else {
            this.restTemplate.getInterceptors().add(getNoTokenInterceptor());
        }
    }

    /**
     * Get the list of containers from Docker Hub
     *
     * @param namespace namespace that contains all the containers
     * @return Container.
     */
    public ListDockerHubContainers getAllContainers(String namespace) {
        String containersListUrl = configuration.getContainersListUrl();
        String url = containersListUrl.replaceAll("%%namespace%%", namespace);
        List<DockerHubContainerBriefInfo> containersList = new ArrayList<>();
        try {
            DockerHubContainerFetcher fetcher = restTemplate.getForObject(url, DockerHubContainerFetcher.class);
            while (true) {
                containersList.addAll(fetcher.getRepositories());
                url = fetcher.getNext();
                if (url == null) {
                    break;
                }
                fetcher = restTemplate.getForObject(url, DockerHubContainerFetcher.class);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        ListDockerHubContainers listDockerHubContainers = new ListDockerHubContainers();
        listDockerHubContainers.setRepositories(containersList);
        return listDockerHubContainers;
    }

    /**
     * Return an specific container by a given namespace (organization) / name of the container
     * (containerName)
     *
     * @param namespace     nameSpace
     * @param containerName containerName
     * @return DockerHubContainer
     */
    public Optional<DockerHubContainer> getContainer(String namespace, String containerName) {
        String containerDetailsUrl = configuration.getContainersDetailsUrl().replaceAll("%%namespace%%", namespace)
                .replaceAll("%%container_name%%", containerName);
        String containerTagsUrl = configuration.getContainerTagsUrl().replaceAll("%%namespace%%", namespace)
                .replaceAll("%%container_name%%", containerName);
        DockerHubContainer container = new DockerHubContainer();
        try {
            DockerHubContainerBriefInfo info = restTemplate.getForObject(containerDetailsUrl, DockerHubContainerBriefInfo.class);
            container.setInfo(info);

            DockerHubTagFetcher tagsFetcher = restTemplate.getForObject(containerTagsUrl, DockerHubTagFetcher.class);
            List<DockerHubTag> tagsList = new ArrayList<>();
            while (true) {
                tagsList.addAll(tagsFetcher.getTags());
                String url = tagsFetcher.getNext();
                if (url == null) {
                    break;
                }
                tagsFetcher = restTemplate.getForObject(url, DockerHubTagFetcher.class);
            }

            container.setTags(tagsList);

            return Optional.of(container);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return Optional.empty();
    }


    private ClientHttpRequestInterceptor getAccessTokenInterceptor(String accessToken) {
        return (request, bytes, execution) -> {
            request.getHeaders().add("Authorization", "access_token " + accessToken);
            return execution.execute(request, bytes);
        };
    }

    private ClientHttpRequestInterceptor getNoTokenInterceptor() {
        return (request, bytes, execution) -> {
            throw new IllegalStateException(
                    "Can't access the API without an access token");
        };
    }
}
