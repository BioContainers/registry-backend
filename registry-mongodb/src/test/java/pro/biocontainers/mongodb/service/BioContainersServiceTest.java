package pro.biocontainers.mongodb.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pro.biocontainers.mongodb.config.FongoTestConfig;
import pro.biocontainers.mongodb.model.BioContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FongoTestConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")

public class BioContainersServiceTest {

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