package pro.biocontainers.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pro.biocontainers.config.FongoTestConfig;
import pro.biocontainers.config.MongoDBConfiguration;
import pro.biocontainers.model.BioContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MongoDBConfiguration.class})
@TestPropertySource(locations = "classpath:application-test.properties")

public class BioContainersServiceLocalhostTest {

    @Autowired
    BioContainersService containersService;

    @Test
    public void indexContainer() {

        BioContainer container = BioContainer.builder()
                .accession("blast")
                .name("blast")
                .url("https://hub.docker.com/r/biocontainers/blast/")
                .build();

        containersService.indexContainer(container);

    }
}