package pro.biocontainers.readers.quayio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import pro.biocontainers.readers.quayio.model.ListShortContainers;
import pro.biocontainers.readers.quayio.model.QuayIOContainer;
import pro.biocontainers.readers.quayio.services.QueryQuayIOService;

import java.util.Optional;


@SpringBootApplication
public class QuayIOReaderApp {

    private static final Logger log = LoggerFactory.getLogger(QuayIOReaderApp.class);

    @Autowired
    private QuayIOConfiguration configuration;

    public static void main(String args[]) {
        SpringApplication.run(QuayIOReaderApp.class);
    }

    @Bean
    public CommandLineRunner run(RestTemplateBuilder builder) {
        return args -> {
            QueryQuayIOService service = new QueryQuayIOService(builder, configuration);
            ListShortContainers listShortContainers = service.getListContainers("biocontainers");
            log.info(listShortContainers.getRepositories().toString());
            listShortContainers.getShortContainersList().forEach(x -> {
                Optional<QuayIOContainer> container = service.getContainer("biocontainers", x.getName());
                if (container.isPresent())
                    log.debug(container.toString());
            });
        };
    }
}