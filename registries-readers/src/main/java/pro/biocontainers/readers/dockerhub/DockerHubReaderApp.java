package pro.biocontainers.readers.dockerhub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import pro.biocontainers.readers.dockerhub.model.DockerHubContainer;
import pro.biocontainers.readers.dockerhub.model.ListDockerHubContainers;
import pro.biocontainers.readers.dockerhub.services.DockerHubQueryService;

import java.util.Optional;


@SpringBootApplication
public class DockerHubReaderApp {

    private static final Logger log = LoggerFactory.getLogger(DockerHubReaderApp.class);

    @Autowired
    private DockerHubConfiguration configuration;

    public static void main(String args[]) {
        SpringApplication.run(DockerHubReaderApp.class);
    }

    @Bean
    public CommandLineRunner run(RestTemplateBuilder builder) {
        return args -> {
            DockerHubQueryService service = new DockerHubQueryService(builder, configuration);
            ListDockerHubContainers containersList = service.getAllContainers("biocontainers");
            log.info("***********containersList*************");
            log.info(containersList.toString());
            log.info("************************");
            containersList.getRepositories().stream().forEach(x -> {
                Optional<DockerHubContainer> container = service.getContainer("biocontainers", x.getName());
                if (container.isPresent()) {
                    log.debug("**********container**************");
                    log.debug(container.toString());
                    log.debug("************************");
                }
            });
        };
    }
}