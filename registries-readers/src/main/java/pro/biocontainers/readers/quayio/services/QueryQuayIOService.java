package pro.biocontainers.readers.quayio.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.biocontainers.readers.quayio.model.ListShortContainers;

@Service
public class QueryQuayIOService {

    RestTemplate restTemplate;

    public QueryQuayIOService(RestTemplateBuilder builder){
        restTemplate = builder.build();
    }

    public ListShortContainers getListContainers(String namespace){
        String url = String.format("https://quay.io/api/v1/repository?popularity=true&last_modified=true&public=true&starred=false&namespace=%s", namespace);
        ListShortContainers listShortContainers = restTemplate.getForObject(url, ListShortContainers.class);
        return listShortContainers;
    }
}
