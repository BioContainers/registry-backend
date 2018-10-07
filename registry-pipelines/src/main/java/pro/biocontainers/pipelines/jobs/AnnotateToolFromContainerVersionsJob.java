package pro.biocontainers.pipelines.jobs;

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
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import pro.biocontainers.data.model.ToolClass;
import pro.biocontainers.data.model.Tuple;
import pro.biocontainers.mongodb.config.MongoDBConfiguration;
import pro.biocontainers.mongodb.model.BioContainerTool;
import pro.biocontainers.mongodb.model.BioContainerToolVersion;
import pro.biocontainers.mongodb.service.BioContainersService;
import pro.biocontainers.pipelines.configs.DataSourceConfiguration;
import pro.biocontainers.pipelines.jobs.AbstractJob;
import pro.biocontainers.pipelines.utilities.BiocontainerTransformer;
import pro.biocontainers.pipelines.utilities.PipelineConstants;
import pro.biocontainers.readers.ExternalID;
import pro.biocontainers.readers.biotools.configs.BioToolsConfiguration;
import pro.biocontainers.readers.biotools.model.BioToolEntry;
import pro.biocontainers.readers.biotools.services.BioToolsQueryService;
import pro.biocontainers.readers.github.configs.GitHubConfiguration;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@EnableBatchProcessing
@Import({ DataSourceConfiguration.class, MongoDBConfiguration.class,
        GitHubConfiguration.class, BioToolsConfiguration.class})
public class AnnotateToolFromContainerVersionsJob extends AbstractJob {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Autowired
    BioContainersService mongoService;

    @Autowired
    BioToolsConfiguration bioToolsConfiguration;



    /**
     * This methods connects to the database read all the Oracle information for public
     * @return
     */
    @Bean
    Step annotateTools() {
        return stepBuilderFactory
                .get(PipelineConstants.StepNames.ANNOTATE_DOCKERHUB_RECIPE.name())
                .tasklet((stepContribution, chunkContext) -> {

                    List<BioContainerToolVersion> allVersions = mongoService.findAllToolVersion();
                    Map<String, List<BioContainerToolVersion>> toolsNames = allVersions.stream()
                            .collect(Collectors
                                    .groupingBy(BioContainerToolVersion::getName, Collectors.toList()));
                    toolsNames.entrySet().stream().forEach( x -> {
                        String name = x.getKey();
                        List<BioContainerToolVersion> versions = x.getValue();
                        List<String> bioTools = new ArrayList<>();

                        versions.stream().forEach( version -> {
                            version.getAdditionalIdentifiers().stream().forEach(id -> {
                                if(id.getKey().equalsIgnoreCase(ExternalID.BIOTOOLS.getName())){
                                    for(String idBio : id.getValue()){
                                        if(idBio.split("/").length > 1){
                                            String[] idClean = idBio.split("/");
                                            String idBioClean = idClean[idClean.length -1];
                                            bioTools.add(idBioClean);
                                        }
                                    }
                                }
                            });
                        });
                        String id = name;
                        List<String> maintainers = versions.stream().
                                map(BioContainerToolVersion::getMaintainers).
                                flatMap(Collection::stream).
                                collect(Collectors.toList());
                        Optional<String> url = versions.stream()
                                .map(BioContainerToolVersion::getHomeURL)
                                .filter(urlVersion -> (urlVersion != null && !urlVersion.isEmpty()))
                                .findFirst();
                        Optional<String> description = versions.stream()
                                .map(BioContainerToolVersion::getDescription)
                                .filter(urlVersion -> (urlVersion != null && !urlVersion.isEmpty()))
                                .findFirst();
                        List<ToolClass> classContainer = versions.stream()
                                .map(BioContainerToolVersion::getToolClasses)
                                .flatMap(Collection::parallelStream)
                                .filter(urlVersion -> (urlVersion != null))
                                .collect(Collectors.toList());
                        List<String> contains = versions.stream()
                                .map(BioContainerToolVersion::getContains)
                                .flatMap(Collection::parallelStream)
                                .filter(urlVersion -> (urlVersion != null))
                                .collect(Collectors.toList());
                        Optional<String> licence = versions.stream()
                                .map(BioContainerToolVersion::getLicense)
                                .filter(urlVersion -> (urlVersion != null))
                                .findFirst();

                        BioContainerTool tool = BioContainerTool.builder()
                                .author(new HashSet<>(maintainers))
                                .name(name)
                                .id(id)
                                .urlHome(url.isPresent()?url.get():null)
                                .description(description.isPresent()?description.get():null)
                                .toolClasses(classContainer)
                                .contains(contains)
                                .license((licence.isPresent())?licence.get():null)
                                .toolVersions(versions.stream()
                                        .map(BioContainerToolVersion::getId)
                                        .collect(Collectors.toSet()))
                                .build();

                        tool.addIdentifiers(versions.stream().map(BioContainerToolVersion::getAdditionalIdentifiers).flatMap(Collection::parallelStream).collect(Collectors.toList()));

                        Optional<BioContainerTool> currentToolOptional = mongoService.findToolByAccession(tool.getId());

                        if(currentToolOptional.isPresent()){
                                BioContainerTool currentTool = currentToolOptional.get();
                                currentTool.addVersion(tool.getMetaVersion());
                                mongoService.updateContainer(currentTool);
                            }else
                                mongoService.indexContainer(tool);

                            log.info(tool.toString());
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
    @Order(3)
    public Job annotateToolFromContainers() {
        return jobBuilderFactory
                .get(PipelineConstants.JobNames.ANNOTATE_CONTAINERS_JOB.getName())
                .incrementer(new RunIdIncrementer())
                .start(annotateTools())
                .next(annotateBioToolsMetadata())
                .build();
    }

    @Bean
    public Step annotateBioToolsMetadata() {
        return stepBuilderFactory
                .get(PipelineConstants.StepNames.ANNOTATE_BIOTOOLS_RECIPE.getName())
                .tasklet((stepContribution, chunkContext) -> {
                    BioToolsQueryService service = new BioToolsQueryService(restTemplateBuilder(), bioToolsConfiguration);
                    List<BioContainerTool> allTools = mongoService.findAll();
                    allTools.stream().forEach( x-> {
                        boolean update = false;
                        for(String idContain: x.getContains()){
                            Optional<BioToolEntry> bioToolEntry = service.getBioToolEntry(idContain);
                            if(bioToolEntry.isPresent() && x.getAdditionalIdentifiers().stream().anyMatch(y -> y.getKey().equalsIgnoreCase(ExternalID.BIOTOOLS.getName()))){
                                x.addBioToolMetadata(BiocontainerTransformer.transformBioToolEntry(bioToolEntry.get()));
                                update = true;
                            }
                        }
                        if(update)
                            mongoService.updateContainer(x);
                        log.info(x.toString());
                    });


                    return RepeatStatus.FINISHED;
                })
                .build();

    }


}

