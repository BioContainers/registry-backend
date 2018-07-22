package pro.biocontainers.readers.quayio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pro.biocontainers.readers.quayio.model.ListShortContainers;
import pro.biocontainers.readers.quayio.model.ShortQuayIOContainer;

import java.util.List;

@SpringBootApplication
public class QuayIOReaderApp {

    private static final Logger log = LoggerFactory.getLogger(QuayIOReaderApp.class);

    public static void main(String args[]) {
        SpringApplication.run(QuayIOReaderApp.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            ListShortContainers listShortContainers = restTemplate.getForObject(
                    "https://quay.io/api/v1/repository?popularity=true&last_modified=true&public=true&starred=false&namespace=biocontainers", ListShortContainers.class);
            log.info(listShortContainers.toString());
        };
    }
}