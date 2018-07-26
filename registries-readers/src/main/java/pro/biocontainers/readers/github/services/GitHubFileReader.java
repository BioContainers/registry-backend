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
        String stringURL =config.getCondaRecipeURL().replace("%%software_name%%", name);
        if(version != null)
            stringURL = stringURL.replace("%%software_version%%", version);
        URL url = new URL(stringURL);
        File fileTemp = File.createTempFile("meta", ".yml");
        FileUtils.copyURLToFile(url, fileTemp);

        CondaRecipe recipe = CondaRecipeReader.parseProperties(fileTemp);
        fileTemp.deleteOnExit();
        return recipe;
    }
}
