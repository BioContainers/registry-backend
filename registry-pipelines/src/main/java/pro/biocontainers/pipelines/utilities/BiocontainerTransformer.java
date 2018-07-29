package pro.biocontainers.pipelines.utilities;

import pro.biocontainers.data.model.ContainerType;
import pro.biocontainers.mongodb.model.BioContainerTool;
import pro.biocontainers.mongodb.model.BioContainerToolVersion;
import pro.biocontainers.mongodb.model.ContainerImage;
import pro.biocontainers.readers.IRegistryContainer;
import pro.biocontainers.readers.Tuple;
import pro.biocontainers.readers.dockerhub.model.DockerHubContainer;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
 * This class
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 25/07/2018.
 */
public class BiocontainerTransformer {

    public static BioContainerTool transformContainerToBiocontainer(IRegistryContainer container, String accessionURL) {
//        String accession = accessionURL.replace("%%name_space%%", container.getNameSpace()).replace("%%software_name%%", container.getName());
//        List<ContainerImage> images = new ArrayList<>();
//        container.getContainerTags().stream().forEach( x-> {
//            images.add(ContainerImage.builder()
//                    .size(x.getValue())
//                    .tag(x.getKey())
//                    .build());
//        });
//        return  BioContainerTool.builder()
//                .name(container.getName())
//                .id(accession)
//                .description(container.getDescription())
////                .lastUpdate(container.getLastUpdated())
////                .pullCount(container.getPullCount())
////                .images(images)
//                .starred(container.isStarred())
//                .build();

        return null;
    }

    /**
     * Convert Docker Container to {@link BioContainerToolVersion}
     * @param container {@link DockerContainer}
     * @param accessionURL url
     * @return BioContainerToolVersion
     */
    public static Optional<BioContainerToolVersion> transformContainerToolVerionToBiocontainer(DockerContainer container,
                                                                                               List<DockerHubContainer> dockerHubContainers,
                                                                                               String accessionURL) {
        if(dockerHubContainers != null){
            List<DockerHubContainer> finalContainers = new ArrayList<>();
            for(DockerHubContainer hubContainer: dockerHubContainers){
                List<Tuple<String, Integer>> sameVersions = hubContainer.getContainerTags().parallelStream().filter(x -> x.getKey().toLowerCase().contains(container.getVersion())).collect(Collectors.toList());
                if(sameVersions.size() > 0){
                    finalContainers.add(hubContainer);
                }
            }
            if(finalContainers.size() == 1){
                DockerHubContainer registryContainer = finalContainers.get(0);
                List<ContainerImage> containerImages = registryContainer.getContainerTags()
                        .stream().map( x-> ContainerImage
                                .builder()
                                .size(x.getValue())
                                .accession(x.getKey())
                                .description(container.getDescription())
                                .containerType(ContainerType.DOCKER)
                                .accession(x.getKey())
                                .build())
                        .collect(Collectors.toList());

                return Optional.of(BioContainerToolVersion
                        .builder()
                        .name(container.getSoftwareName())
                        .version(container.getSoftwareVersion())
                        .hashName(GeneralUtils.getHashName(container.getSoftwareName()))
                        .containerImages(containerImages)
                        .build());
            }
        }
        return Optional.empty();

    }
}
