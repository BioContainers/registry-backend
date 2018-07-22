package pro.biocontainers.readers.quayio.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import pro.biocontainers.readers.quayio.model.ListShortContainers;
import pro.biocontainers.readers.quayio.model.ShortQuayIOContainer;

import java.io.IOException;

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
                    .add(getBearerTokenInterceptor(accessToken));
        } else {
            this.restTemplate.getInterceptors().add(getNoTokenInterceptor());
        }
    }

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

    private ClientHttpRequestInterceptor  getBearerTokenInterceptor(String accessToken) {
        ClientHttpRequestInterceptor interceptor = (request, bytes, execution) -> {
            request.getHeaders().add("Authorization", "access_token " + accessToken);
            return execution.execute(request, bytes);
        };
        return interceptor;
    }

    private ClientHttpRequestInterceptor getNoTokenInterceptor() {
        return (request, bytes, execution) -> {
            throw new IllegalStateException(
                    "Can't access the API without an access token");
        };
    }
}
