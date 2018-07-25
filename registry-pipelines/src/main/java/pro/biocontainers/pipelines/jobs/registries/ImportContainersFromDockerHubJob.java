package pro.biocontainers.pipelines.jobs.registries;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import pro.biocontainers.mongodb.config.MongoDBConfiguration;
import pro.biocontainers.mongodb.service.BioContainersService;
import pro.biocontainers.pipelines.configs.DataSourceConfiguration;
import pro.biocontainers.pipelines.jobs.AbstractJob;
import pro.biocontainers.pipelines.utilities.BiocontainerTransformer;
import pro.biocontainers.pipelines.utilities.PipelineConstants;
import pro.biocontainers.readers.dockerhub.DockerHubConfiguration;
import pro.biocontainers.readers.dockerhub.model.DockerHubContainer;
import pro.biocontainers.readers.dockerhub.services.DockerHubQueryService;
import pro.biocontainers.readers.quayio.QuayIOConfiguration;
import pro.biocontainers.readers.quayio.model.QuayIOContainer;
import pro.biocontainers.readers.quayio.services.QueryQuayIOService;

import java.util.Optional;

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
@Import({ DataSourceConfiguration.class, DockerHubConfiguration.class, QuayIOConfiguration.class, MongoDBConfiguration.class})
public class ImportContainersFromDockerHubJob extends AbstractJob {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Autowired
    DockerHubConfiguration dockerHubConfig;

    @Autowired
    QuayIOConfiguration quayIOConfiguration;

    @Autowired
    BioContainersService mongoService;

    @Value("${public-url.dockerhub}")
    String dockerHubRegistry;

    @Value("${public-url.quayio}")
    String quayIOHubRegistry;


    /**
     * This methods connects to the database read all the Oracle information for public
     * @return
     */
    @Bean
    Step readContainersFromDockerHub() {
        return stepBuilderFactory
                .get(PipelineConstants.StepNames.READ_DOCKERHUB_REGISTRY_LIST.name())
                .tasklet((stepContribution, chunkContext) -> {
                    DockerHubQueryService service = new DockerHubQueryService(restTemplateBuilder(), dockerHubConfig);
                    service.getAllContainers("biocontainers").getRepositories().forEach(x -> {
                        Optional<DockerHubContainer> container = service.getContainer("biocontainers", x.getName());
                        if (container.isPresent()) {
                            mongoService.indexContainer(BiocontainerTransformer.transformContainerToBiocontainer(container.get(), dockerHubRegistry));
                            log.debug("**********container**************");
                            log.debug(container.toString());
                            log.debug("************************");
                        }
                    });
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step readContainersFromQuayIO() {
        return stepBuilderFactory
                .get(PipelineConstants.StepNames.READ_QUAYIO_REGISTRY_LIST.name())
                .tasklet((stepContribution, chunkContext) -> {
                    QueryQuayIOService service = new QueryQuayIOService(restTemplateBuilder(), quayIOConfiguration);
                    service.getListContainers("biocontainers").getRepositories().forEach(x -> {
                        Optional<QuayIOContainer> container = service.getContainer("biocontainers", x.getName());
                        if (container.isPresent()) {
                            mongoService.indexContainer(BiocontainerTransformer.transformContainerToBiocontainer(container.get(), quayIOHubRegistry));
                            log.debug("**********container**************");
                            log.debug(container.toString());
                            log.debug("************************");
                        }
                    });
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
                .next(readContainersFromQuayIO())
                .build();
    }



}

