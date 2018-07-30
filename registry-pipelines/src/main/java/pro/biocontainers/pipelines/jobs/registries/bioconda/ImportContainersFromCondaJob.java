package pro.biocontainers.pipelines.jobs.registries.bioconda;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.yaml.snakeyaml.reader.ReaderException;
import pro.biocontainers.mongodb.config.MongoDBConfiguration;
import pro.biocontainers.mongodb.model.BioContainerToolVersion;
import pro.biocontainers.mongodb.service.BioContainersService;
import pro.biocontainers.pipelines.configs.DataSourceConfiguration;
import pro.biocontainers.pipelines.jobs.AbstractJob;
import pro.biocontainers.pipelines.utilities.BiocontainerTransformer;
import pro.biocontainers.pipelines.utilities.PipelineConstants;
import pro.biocontainers.readers.dockerhub.model.DockerHubContainer;
import pro.biocontainers.readers.dockerhub.services.DockerHubQueryService;
import pro.biocontainers.readers.github.configs.GitHubConfiguration;
import pro.biocontainers.readers.github.services.GitHubFileNameList;
import pro.biocontainers.readers.github.services.GitHubFileReader;
import pro.biocontainers.readers.quayio.QuayIOConfiguration;
import pro.biocontainers.readers.quayio.model.QuayIOContainer;
import pro.biocontainers.readers.quayio.services.QueryQuayIOService;
import pro.biocontainers.readers.utilities.conda.model.CondaRecipe;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@Import({ DataSourceConfiguration.class, QuayIOConfiguration.class, MongoDBConfiguration.class, GitHubConfiguration.class})
public class ImportContainersFromCondaJob extends AbstractJob {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Autowired
    QuayIOConfiguration quayIOConfiguration;

    @Autowired
    BioContainersService mongoService;

    @Autowired
    GitHubConfiguration gitHubConfiguration;

    GitHubFileReader fileReaderService;

    RestTemplateBuilder builder;

    @Value("${public-url.dockerhub}")
    String dockerHubRegistry;

    @Value("${public-url.quayio}")
    String quayIOHubRegistry;


    @Bean
    public RestTemplateBuilder getRestTemplate() {
        builder = new RestTemplateBuilder();
        return builder;
    }

    @Bean
    GitHubFileReader getFileReaderService(){
        fileReaderService = new GitHubFileReader(gitHubConfiguration, builder);
        return this.fileReaderService;
    }


    /**
     * This methods connects to the database read all the Oracle information for public.
     * @return
     */
    @Bean
    Step readContainersFromQUAYIO() {
        return stepBuilderFactory
                .get(PipelineConstants.StepNames.READ_QUAYIO_REGISTRY_LIST.name())
                .tasklet((stepContribution, chunkContext) -> {


                    GitHubFileNameList condaFileList = fileReaderService.getCondaFiles();
                    Map<String, Set<CondaRecipe>> toolNames = new ConcurrentHashMap<>();

                    condaFileList.getTree().parallelStream()
                            .filter( x-> (x.getPath().toLowerCase().contains(PipelineConstants.META_YAML.toLowerCase()) || x.getPath().toLowerCase().contains(PipelineConstants.META_YML.toLowerCase())))
                            .forEach( fileName -> {
                                String name = fileName.getPath();
                                String[] nameValues = name.split("\\/");
                                if(nameValues.length > 1){
                                    Set<CondaRecipe> values = (toolNames.containsKey(nameValues[1])) ? toolNames.get(nameValues[1]) :new HashSet<>() ;
                                    try {
                                        CondaRecipe container = fileReaderService.parseCondaRecipe(fileName.getPath());
                                        values.add(container);
                                        toolNames.put(nameValues[1], values);
                                    } catch (IOException | ReaderException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    log.info("The Number of Conda Recipes -- " + toolNames.size());

                    QueryQuayIOService service = new QueryQuayIOService(restTemplateBuilder(), quayIOConfiguration);
                    List<Optional<QuayIOContainer>> registryContainers = service.getListContainers("biocontainers")
                            .getShortContainersList()
                            .stream()
                            .map(x -> service.getContainer("biocontainers", x.getName()))
                            .collect(Collectors.toList());

                    log.info("Number of containers to be inserted -- " + registryContainers.size());


                    toolNames.forEach((key, value) -> value.stream()
                            .forEach(containerVersion -> {
                                List<QuayIOContainer> registryContainer = new ArrayList<>();
                                for (Optional<QuayIOContainer> rContainer : registryContainers) {
                                    if (rContainer.isPresent() && rContainer.get().getName().equalsIgnoreCase(containerVersion.getSoftwareName()))
                                        registryContainer.add(rContainer.get());
                                }

                                Optional<BioContainerToolVersion> mongoToolVersion = BiocontainerTransformer.transformCondaToolVersionToBiocontainer(containerVersion, registryContainer, "quay.io");
                        if (mongoToolVersion.isPresent()) {
                            mongoService.indexToolVersion(mongoToolVersion.get());
                            log.info("New BioContainerTool Version to store -- " + mongoToolVersion.get().getName());
                        }
                    }));




                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    /**
     * Defines the job to Sync all the projects from OracleDB into MongoDB database.
     *
     * @return the calculatePrideArchiveDataUsage job
     */
    @Bean
    public Job importQuayIOToMongoDB() {
        return jobBuilderFactory
                .get(PipelineConstants.JobNames.READ_QUAYIO_CONTAINERS_JOB.getName())
                .start(readContainersFromQUAYIO())
                .build();
    }
}
