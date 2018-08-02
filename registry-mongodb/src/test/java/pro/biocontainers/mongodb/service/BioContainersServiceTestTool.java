package pro.biocontainers.mongodb.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pro.biocontainers.mongodb.config.FongoTestConfig;
import pro.biocontainers.mongodb.model.BioContainerTool;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FongoTestConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")

public class BioContainersServiceTestTool {

    @Autowired
    BioContainersService containersService;

    @Test
    public void indexContainer() {

        BioContainerTool container = BioContainerTool.builder()
                .id("blast")
                .name("blast")
                .build();

        containersService.indexContainer(container);

    }
}