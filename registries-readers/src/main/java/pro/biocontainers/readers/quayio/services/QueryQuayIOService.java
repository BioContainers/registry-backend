package pro.biocontainers.readers.quayio.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pro.biocontainers.readers.quayio.model.ListShortContainers;
import pro.biocontainers.readers.quayio.model.QuayIOContainer;

import java.util.Optional;

@Service
@Log4j2
public class QueryQuayIOService {

    RestTemplate restTemplate;

    public QueryQuayIOService(RestTemplateBuilder builder){
        restTemplate = builder.build();


    }

    public void setToken(String accessToken){
        if (accessToken != null) {
            this.restTemplate.getInterceptors()
                    .add(getAccessTokenInterceptor(accessToken));
        } else {
            this.restTemplate.getInterceptors().add(getNoTokenInterceptor());
        }
    }

    /**
     * Get the list of containers from Quay.io
     * @param namespace namespace that contains all the containers
     * @return Container.
     */
    public ListShortContainers getListContainers(String namespace){
        String url = String.format("https://quay.io/api/v1/repository?popularity=true&last_modified=true&public=true&starred=false&namespace=%s", namespace);
        ListShortContainers listShortContainers = new ListShortContainers();
        try{
             listShortContainers = restTemplate.getForObject(url, ListShortContainers.class);
        }catch (RestClientException ex){
            log.error(ex.getMessage());
        }
        return listShortContainers;
    }

    /**
     * Return an specific container by a given namespace (organization) / name of the container
     * (containerName)
     * @param namespace nameSpace
     * @param containerName containerName
     * @return QuayIOContainer
     */
    public Optional<QuayIOContainer> getContainer(String namespace, String containerName){
        String url = String.format("https://quay.io/api/v1/repository/%s/%s", namespace, containerName);
        try{
            QuayIOContainer container = restTemplate.getForObject(url, QuayIOContainer.class);
            return Optional.of(container);
        }catch (RestClientException ex){
            log.error(ex.getMessage());
        }
        return Optional.empty() ;
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
