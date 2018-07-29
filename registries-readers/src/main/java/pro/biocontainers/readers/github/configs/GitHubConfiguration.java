package pro.biocontainers.readers.github.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource(value = {"classpath:application.properties"})
public class GitHubConfiguration {

    @Value("${github.docker-containers-recipes}")
    String dockerRecipeURL;

    @Value("${github.bioconda-containers-recipes}")
    String condaRecipeURL;

    @Value("${github.docker-api-containers-urls}")
    String gitHubAPIDockerFiles;

    @Value("${githug.conda-api-containers-urls}")
    String gitHubAPICondaFiles;

}
