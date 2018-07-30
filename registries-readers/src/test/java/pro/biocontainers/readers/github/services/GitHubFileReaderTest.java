package pro.biocontainers.readers.github.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pro.biocontainers.readers.github.configs.GitHubConfiguration;
import pro.biocontainers.readers.utilities.conda.model.CondaRecipe;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GitHubConfiguration.class})
public class GitHubFileReaderTest {

    @Autowired
    GitHubConfiguration config;


    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Test
    public void parseCondaRecipe() {
        GitHubFileReader reader = new GitHubFileReader(config, restTemplateBuilder());
        try {
            CondaRecipe recipe = reader.parseCondaRecipe("abyss", "2.1.0");
            Assert.assertTrue(recipe.getSoftwareName().equalsIgnoreCase("abyss"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseDockerfileRecipe() {
        GitHubFileReader reader = new GitHubFileReader(config, restTemplateBuilder());
        try {
            DockerContainer recipe = reader.parseDockerRecipe("abacas", "1.3.1-3-deb");
            Assert.assertTrue(recipe.getSoftwareName().equalsIgnoreCase("abacas"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDockerFiles(){
        GitHubFileReader reader = new GitHubFileReader(config, restTemplateBuilder());
        GitHubFileNameList files = reader.getDockerFiles();
        Assert.assertTrue(files.tree.size() > 0 );
    }


}