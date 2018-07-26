package pro.biocontainers.readers.github.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pro.biocontainers.readers.github.configs.GitHubConfiguration;
import pro.biocontainers.readers.utilities.conda.model.CondaRecipe;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GitHubConfiguration.class})
public class GitHubFileReaderTest {

    @Autowired
    GitHubConfiguration config;

    @Test
    public void parseCondaRecipe() {
        GitHubFileReader reader = new GitHubFileReader(config);
        try {
            CondaRecipe recipe = reader.parseCondaRecipe("abyss", "2.1.0");
            Assert.assertTrue(recipe.getSoftwareName().equalsIgnoreCase("abyss"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}