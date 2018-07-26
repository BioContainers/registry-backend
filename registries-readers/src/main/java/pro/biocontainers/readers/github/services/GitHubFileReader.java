package pro.biocontainers.readers.github.services;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import pro.biocontainers.readers.github.configs.GitHubConfiguration;
import pro.biocontainers.readers.utilities.conda.CondaRecipeReader;
import pro.biocontainers.readers.utilities.conda.model.CondaRecipe;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
public class GitHubFileReader {

    GitHubConfiguration config;

    public GitHubFileReader(GitHubConfiguration config){
        this.config = config;
    }

    public CondaRecipe parseCondaRecipe(String name, String version) throws IOException {
        URL url = new URL(config.getCondaRecipeURL().replace("%%software_name%%", name).replace("%%software_version%%", version));
        File fileTemp = File.createTempFile("meta", ".yml");
        FileUtils.copyURLToFile(url, fileTemp);

        CondaRecipe recipe = CondaRecipeReader.parseProperties(fileTemp);
        fileTemp.deleteOnExit();
        return recipe;
    }
}
