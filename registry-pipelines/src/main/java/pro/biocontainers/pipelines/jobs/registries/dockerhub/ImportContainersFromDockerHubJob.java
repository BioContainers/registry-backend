package pro.biocontainers.pipelines.jobs.registries.dockerhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pro.biocontainers.mongodb.config.MongoDBConfiguration;
import pro.biocontainers.mongodb.model.BioContainerToolVersion;
import pro.biocontainers.mongodb.service.BioContainersService;
import pro.biocontainers.pipelines.configs.DataSourceConfiguration;
import pro.biocontainers.pipelines.jobs.AbstractJob;
import pro.biocontainers.pipelines.utilities.BiocontainerTransformer;
import pro.biocontainers.pipelines.utilities.PipelineConstants;
import pro.biocontainers.readers.dockerhub.DockerHubConfiguration;
import pro.biocontainers.readers.dockerhub.model.DockerHubContainer;
import pro.biocontainers.readers.dockerhub.services.DockerHubQueryService;
import pro.biocontainers.readers.github.configs.GitHubConfiguration;
import pro.biocontainers.readers.github.services.GitHubFileNameList;
import pro.biocontainers.readers.github.services.GitHubFileReader;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class Job sync all the projects from MongoDB to SolrCloud. The first approach would be to index the projects from the
 * MongoDB and then other jobs can be used to index the from Oracle.
 *
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 13/06/2018.
 */
@Configuration
@Slf4j
@EnableBatchProcessing
@Import({ DataSourceConfiguration.class, DockerHubConfiguration.class, MongoDBConfiguration.class, GitHubConfiguration.class})
public class ImportContainersFromDockerHubJob extends AbstractJob {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Autowired
    DockerHubConfiguration dockerHubConfig;

    @Autowired
    BioContainersService mongoService;

    @Autowired
    GitHubConfiguration gitHubConfiguration;

    GitHubFileReader fileReaderService;

    RestTemplateBuilder builder;

    @Value("${public-url.dockerhub}")
    String dockerHubRegistry;

    @Value("${public-url.quay-io}")
    String quayIOHubRegistry;

    @Value("${biocontainers-registry}")
    String bioContainersURL;

    @Value("${biocontainers-registry-version}")
    String bioContainersVersionURL;

    @Value("${biocontainers-registry-image}")
    String bioContainersImageURL;


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
    Step readContainersFromDockerHub() {
        return stepBuilderFactory
                .get(PipelineConstants.StepNames.READ_DOCKERHUB_REGISTRY_LIST.name())
                .tasklet((stepContribution, chunkContext) -> {

                    /** Read containers recipes from BioContainers GitHub **/

                    GitHubFileNameList dockerfileList = fileReaderService.getDockerFiles();
                    Map<String, Set<DockerContainer>> toolNames = new ConcurrentHashMap<>();

                    dockerfileList.getTree().parallelStream()
                            .filter( x-> x.getPath().toLowerCase().contains(PipelineConstants.DOCKERFILE.toLowerCase()))
                            .forEach( fileName -> {
                                String name = fileName.getPath();
                                String[] nameValues = name.split("\\/");
                                if(nameValues.length > 1){
                                    Set<DockerContainer> values = (toolNames.containsKey(nameValues[0])) ? toolNames.get(nameValues[0]) :new HashSet<>() ;
                                    try {
                                        DockerContainer container = fileReaderService.parseDockerRecipe(nameValues[0], nameValues[1]);
                                        container.setVersion(nameValues[1]);
                                        values.add(container);
                                        toolNames.put(nameValues[0], values);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                    log.info("Number of DockerFile recipes -- " + toolNames.size());

                    DockerHubQueryService service = new DockerHubQueryService(restTemplateBuilder(), dockerHubConfig);
                    List<Optional<DockerHubContainer>> registryContainers = service.getAllContainers("biocontainers")
                            .getRepositories()
                            .stream()
                            .map(x -> service.getContainer("biocontainers", x.getName()))
                            .collect(Collectors.toList());

                    log.info("Number of containers to be inserted -- " + registryContainers.size());


                    toolNames.forEach((key, value) -> value.stream().forEach(containerVersion -> {
                        List<DockerHubContainer> registryContainer = new ArrayList<>();
                        for (Optional<DockerHubContainer> rContainer : registryContainers) {

                            if (rContainer.isPresent() && rContainer.get().getName().equalsIgnoreCase(containerVersion.getSoftwareName()))
                                registryContainer.add(rContainer.get());
                        }
                        Optional<BioContainerToolVersion> mongoToolVersion = BiocontainerTransformer.transformContainerToolVersionToBiocontainer(containerVersion, registryContainer, "biocontainers");
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
    public Job importDockerHubToMongoDB() {
        return jobBuilderFactory
                .get(PipelineConstants.JobNames.READ_DOCKERHUB_CONTAINERS_JOB.getName())
                .start(readContainersFromDockerHub())
                .build();
    }



}

