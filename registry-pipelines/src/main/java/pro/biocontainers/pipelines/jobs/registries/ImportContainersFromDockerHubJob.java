package pro.biocontainers.pipelines.jobs.registries;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pro.biocontainers.pipelines.configs.DataSourceConfiguration;
import pro.biocontainers.pipelines.jobs.AbstractJob;
import pro.biocontainers.pipelines.utilities.PipelineConstants;

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
@Import({ DataSourceConfiguration.class})
public class ImportContainersFromDockerHubJob extends AbstractJob {


    /**
     * This methods connects to the database read all the Oracle information for public
     * @return
     */
    @Bean
    Step readContainersFromDockerHub() {
        return stepBuilderFactory
                .get(PipelineConstants.StepNames.READ_DOCKERHUB_REGISTRY_LIST.name())
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println();
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
    public Job syncMongoProjectToSolrCloudJob() {
        return jobBuilderFactory
                .get(PipelineConstants.JobNames.READ_DOCKERHUB_CONTAINERS_JOB.getName())
                .start(readContainersFromDockerHub())
                .build();
    }




}

