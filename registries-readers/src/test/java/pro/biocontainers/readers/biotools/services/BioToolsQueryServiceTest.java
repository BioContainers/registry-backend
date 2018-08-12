package pro.biocontainers.readers.biotools.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pro.biocontainers.readers.biotools.configs.BioToolsConfiguration;
import pro.biocontainers.readers.biotools.model.BioToolEntry;
import pro.biocontainers.readers.github.configs.GitHubConfiguration;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BioToolsConfiguration.class})
public class BioToolsQueryServiceTest {

    @Autowired
    BioToolsConfiguration configuration;

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }


    @Test
    public void getBioToolEntry() {

        BioToolsQueryService service = new BioToolsQueryService(restTemplateBuilder(), configuration);

        Optional<BioToolEntry> toolOptional = service.getBioToolEntry("roc");
        if(toolOptional.isPresent()){
            Assert.assertTrue(toolOptional.get().getId().equalsIgnoreCase("roc"));

            Assert.assertTrue(toolOptional.get().getDownloadURLS().length == 1);

            Assert.assertTrue(toolOptional.get().getLicense().equalsIgnoreCase("Artistic-2.0"));
        }


    }
}