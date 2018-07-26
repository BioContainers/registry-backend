package pro.biocontainers.pipelines.jobs.registries;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 26/07/2018.
 */

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
import pro.biocontainers.readers.utilities.conda.model.CondaRecipe;

import java.io.IOException;
import java.util.Optional;

@Configuration
@Slf4j
@Import({ DataSourceConfiguration.class, MongoDBConfiguration.class, GitHubConfiguration.class})
public class AnnotateContainersFromRecipeJob extends AbstractJob {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Autowired
    BioContainersService mongoService;

    @Autowired
    GitHubConfiguration gitHubConfiguration;

    GitHubFileReader fileReaderService;

    @Bean
    GitHubFileReader getFileReaderService(){
        fileReaderService = new GitHubFileReader(gitHubConfiguration);
        return this.fileReaderService;
    }

    @Value("${public-url.dockerhub}")
    String dockerHubRegistry;

    @Value("${public-url.quayio}")
    String quayIOHubRegistry;


    /**
     * This methods connects to the database read all the Oracle information for public
     * @return
     */
//    @Bean
//    Step annotateContainersFromDockerHub() {
//        return stepBuilderFactory
//                .get(PipelineConstants.StepNames.ANNOTATE_DOCKERHUB_RECIPE.name())
//                .tasklet((stepContribution, chunkContext) -> {
//                    mongoService.findAll().parallelStream().forEach( bioContainer -> {
//                        try {
//                            CondaRecipe recipe = fileReaderService.parseCondaRecipe(bioContainer.getName(), bioContainer.getVersion());
//                            bioContainer.setDescription(recipe.getDescription());
//                            mongoService.updateContainer(bioContainer);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    });
//
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }

    @Bean
    public Step annnotateContainersFromQuayIO() {
        return stepBuilderFactory
                .get(PipelineConstants.StepNames.ANNOTATE_QUAYIO_RECIPE.name())
                .tasklet((stepContribution, chunkContext) -> {
                    mongoService.findAll().parallelStream().filter(x -> x.getAccession().contains(PipelineConstants.QUAYIO)).forEach( bioContainer -> {
                        try {
                            CondaRecipe recipe = fileReaderService.parseCondaRecipe(bioContainer.getName(), bioContainer.getVersion());
                            bioContainer.setDescription(recipe.getDescription());
                            mongoService.updateContainer(bioContainer);
                        } catch (IOException e) {
                            e.printStackTrace();
                            log.error("The container recipe hasn't been found in the conda-recipes -- " + bioContainer.getAccession());
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
    public Job annotateCondaDockerContainers() {
        return jobBuilderFactory
                .get(PipelineConstants.JobNames.ANNOTATE_CONTAINERS_JOB.getName())
//                .start(annotateContainersFromDockerHub())
                .start(annnotateContainersFromQuayIO())
                .build();
    }



}

