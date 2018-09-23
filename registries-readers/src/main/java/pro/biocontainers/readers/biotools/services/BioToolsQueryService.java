package pro.biocontainers.readers.biotools.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.biocontainers.readers.biotools.configs.BioToolsConfiguration;
import pro.biocontainers.readers.biotools.model.BioToolEntry;
import java.util.Optional;

@Service
@Log4j2
public class BioToolsQueryService {

    private RestTemplate restTemplate;

    private BioToolsConfiguration configuration;

    public BioToolsQueryService(RestTemplateBuilder builder, BioToolsConfiguration configuration) {
        restTemplate = builder.build();
        this.configuration = configuration;
    }

    /**
     * Get Bio.tools information for an specific container.
     *
     * @return Container.
     */
    public Optional<BioToolEntry> getBioToolEntry(String biotoolName) {
        String containersListUrl = configuration.getBiotoolsAPIURL();
        String url = containersListUrl.replaceAll("%%namespace%%", biotoolName);
        try {
            BioToolEntry fetcher = restTemplate.getForObject(url, BioToolEntry.class);
            return Optional.of(fetcher);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return Optional.empty();
    }



}
