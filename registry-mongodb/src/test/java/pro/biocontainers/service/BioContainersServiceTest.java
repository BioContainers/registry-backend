package pro.biocontainers.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pro.biocontainers.config.FongoTestConfig;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FongoTestConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")

public class BioContainersServiceTest {

    @Test
    public void indexContainer() {


    }
}