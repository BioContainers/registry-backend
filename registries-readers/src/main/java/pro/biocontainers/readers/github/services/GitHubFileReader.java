package pro.biocontainers.readers.github.services;

import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.biocontainers.readers.github.configs.GitHubConfiguration;
import pro.biocontainers.readers.utilities.conda.CondaRecipeReader;
import pro.biocontainers.readers.utilities.conda.model.CondaRecipe;
import pro.biocontainers.readers.utilities.dockerfile.DockerParser;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;

@Service
public class GitHubFileReader {

    GitHubConfiguration config;
    RestTemplate restTemplate;

    public GitHubFileReader(GitHubConfiguration config, RestTemplateBuilder builder){
        this.config = config;
        this.restTemplate = builder.build();
    }

    /**
     * Download Recipe from Conda
     * @param name
     * @param version
     * @return
     * @throws IOException
     */
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

    /**
     * Download Recipe from Dockerfile
     * @param name
     * @param version
     * @return
     * @throws IOException
     */
    public DockerContainer parseDockerRecipe(String name, String version) throws IOException {
        String stringURL =config.getDockerRecipeURL().replace("%%software_name%%", name);
        if(version != null)
            stringURL = stringURL.replace("%%software_version%%", version);
        URL url = new URL(stringURL);

        File tempDir = Files.createTempDir();
        String tempFileName = tempDir.getAbsolutePath() + FileSystems.getDefault().getSeparator() + "Dockerfile";
        File fileTemp = new File(tempFileName);
        FileUtils.copyURLToFile(url, fileTemp);

        DockerParser parser = new DockerParser(fileTemp.getParent(), fileTemp.getAbsolutePath());
        DockerContainer container = parser.getParsedDockerfileObject(fileTemp);

        fileTemp.deleteOnExit();
        tempDir.deleteOnExit();

        return container;
    }

    /**
     * Get the DockerFile 
     * @return
     */
    public DockerFileNameList getDockerFiles() {
        String url = config.getGitHubAPIFiles();
        return restTemplate.getForObject(url, DockerFileNameList.class);


    }
}
