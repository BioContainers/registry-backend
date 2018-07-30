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
import pro.biocontainers.mongodb.config.MongoDBConfiguration;
import pro.biocontainers.mongodb.service.BioContainersService;
import pro.biocontainers.pipelines.configs.DataSourceConfiguration;
import pro.biocontainers.pipelines.jobs.AbstractJob;
import pro.biocontainers.pipelines.utilities.PipelineConstants;
import pro.biocontainers.readers.github.configs.GitHubConfiguration;
import pro.biocontainers.readers.github.services.GitHubFileReader;
import pro.biocontainers.readers.quayio.QuayIOConfiguration;

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
//                    QueryQuayIOService service = new QueryQuayIOService(restTemplateBuilder(), quayIOConfiguration);
//                    List<Optional<QuayIOContainer>> registryContainers = service
//                            .getListContainers("biocontainers").getRepositories()
//                            .parallelStream()
//                            .map(x -> {
//                                return service.getContainer(x.getNamespace(), x.getName());
//                            })
//                            .collect(Collectors.toList());
//
//                    log.info("Number of containers to be inserted -- " + registryContainers.size());
//
//                    GitHubFileNameList dockerfileList = fileReaderService.getDockerFiles();
//                    Map<String, Set<DockerContainer>> toolNames = new ConcurrentHashMap<>();
//
//                    dockerfileList.getTree().parallelStream()
//                            .filter( x-> x.getPath().toLowerCase().contains(PipelineConstants.DOCKERFILE.toLowerCase()))
//                            .forEach( fileName -> {
//                                String name = fileName.getPath();
//                                String[] nameValues = name.split("\\/");
//                                if(nameValues.length > 1){
//                                    Set<DockerContainer> values = (toolNames.containsKey(nameValues[0]))? toolNames.get(nameValues[0]) :new HashSet<>() ;
//                                    try {
//                                        DockerContainer container = fileReaderService.parseDockerRecipe(nameValues[0], nameValues[1]);
//                                        container.setVersion(nameValues[1]);
//                                        values.add(container);
//                                        toolNames.put(nameValues[0], values);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });

//                    log.info("Number of DockerFile recipes -- " + toolNames.size());

//                    toolNames.entrySet().stream().forEach( container -> {
//                        container.getValue().stream().forEach( containerVersion -> {
//                            List<DockerHubContainer> registryContainer = new ArrayList<>();
//                            for(Optional<DockerHubContainer> rContainer: registryContainers) {
//
//                                if (rContainer.isPresent() && rContainer.get().getName().equalsIgnoreCase(containerVersion.getSoftwareName()))
//                                    registryContainer.add(rContainer.get());
//                            }
//                            Optional<BioContainerToolVersion> mongoToolVersion = BiocontainerTransformer.transformContainerToolVersionToBiocontainer(containerVersion,registryContainer, dockerHubRegistry);
//                            if(mongoToolVersion.isPresent())
//                                log.info("New BioContainerTool Version to store -- " + mongoToolVersion.get().getName());
////                                mongoService.indexToolVersion(mongoToolVersion);
//                        });
//
//                    });


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
