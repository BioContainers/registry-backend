package pro.biocontainers.pipelines.utilities;

import lombok.extern.log4j.Log4j;
import pro.biocontainers.data.model.ContainerType;
import pro.biocontainers.data.model.Tuple;
import pro.biocontainers.mongodb.model.BioContainerTool;
import pro.biocontainers.mongodb.model.BioContainerToolVersion;
import pro.biocontainers.mongodb.model.ContainerImage;
import pro.biocontainers.readers.IRegistryContainer;
import pro.biocontainers.readers.dockerhub.model.DockerHubContainer;
import pro.biocontainers.readers.quayio.model.QuayIOContainer;
import pro.biocontainers.readers.utilities.conda.model.CondaRecipe;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;
import pro.biocontainers.readers.utilities.dockerfile.models.commands.Label;
import pro.biocontainers.readers.utilities.dockerfile.models.commands.Maintainer;

import java.util.*;
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
@Log4j
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
     * @return BioContainerToolVersion
     */
    public static Optional<BioContainerToolVersion> transformCondaToolVersionToBiocontainer(CondaRecipe container,
                                                                                                List<QuayIOContainer> dockerQUAYIOContainers,
                                                                                                String accessionCommand) {
        // Parse the DockerContainers
        List<QuayIOContainer> finalContainers = new ArrayList<>();
        if(dockerQUAYIOContainers != null){
            for(QuayIOContainer hubContainer: dockerQUAYIOContainers){
                List<Tuple<String, Integer>> sameVersions = hubContainer
                        .getContainerTags()
                        .parallelStream()
                        .filter(x -> x.getKey().toLowerCase().contains(container.getSoftwareVersion()))
                        .collect(Collectors.toList());
                if(sameVersions.size() > 0){
                    finalContainers.add(hubContainer);
                }
            }
        }

        Set<ContainerImage> containerImages = new HashSet<>();
        //Todo: See how to check where the contributors of the containers are stored.
        List<String> maintainers = new ArrayList<>();
        maintainers.add("BioConda Team <https://github.com/bioconda>");
        maintainers.addAll(container.getMaintainers());

        if(finalContainers.size() > 0){
            finalContainers.forEach(x -> x.getContainerTags().forEach(y -> {
                ContainerImage containerImage = ContainerImage.builder()
                        .size(y.getValue())
                        .fullTag(buildDockerCommand(container.getSoftwareName(), y.getKey(), accessionCommand))
                        .description(container.getDescription())
                        .containerType(ContainerType.DOCKER)
                        .lastUpdate(x.getLastUpdated())
                        .tag(y.getKey())
                        .maintainer(maintainers)
                        .build();
                containerImages.add(containerImage);
            }));
        }

        List<QuayIOContainer> finalUpdates = finalContainers.stream().filter(x -> x.getLastUpdated() != null)
                .sorted(Comparator.comparing(QuayIOContainer::getLastUpdated)).collect(Collectors.toList());

        Date finalUpdate = null;
        if(finalUpdates.size() > 0 && finalUpdates.stream().findFirst().isPresent())
            finalUpdate = finalUpdates.stream().findFirst().get().getLastUpdated();

        if(container.getSoftwareName() == null)
            log.error("Not name for contain -- ");
        return Optional
                .of(BioContainerToolVersion
                        .builder()
                        .name(container.getSoftwareName())
                        .version(container.getSoftwareVersion())
                        .description(container.getDescription())
                        .lastUpdate(finalUpdate)
                        .hashName(GeneralUtils.getHashName(container.getSoftwareName()))
                        .containerImages(containerImages)
                        .license(container.getLicense())
                        .docURL(container.getDocURL())
                        .homeURL(container.getHomeURL())
                        .text(container.toString())
                        .additionalIdentifiers(container
                                .getExternalIds()
                                .entrySet()
                                .stream()
                                .map(x -> new Tuple<>(x.getKey(), x.getValue()))
                                .collect(Collectors.toList()))
                        .build());



    }

    /**
     * Convert Docker Container to {@link BioContainerToolVersion}
     * @param container {@link DockerContainer}
     * @return BioContainerToolVersion
     */
    public static Optional<BioContainerToolVersion> transformContainerToolVersionToBiocontainer(DockerContainer container,
                                                                                                List<DockerHubContainer> dockerHubContainers,
                                                                                                String accessionCommand) {
        // Parse the DockerContainers
        List<DockerHubContainer> finalContainers = new ArrayList<>();
        if(dockerHubContainers != null){
            for(DockerHubContainer hubContainer: dockerHubContainers){
                List<Tuple<String, Integer>> sameVersions = hubContainer
                        .getContainerTags()
                        .parallelStream()
                        .filter(x -> x.getKey().toLowerCase().contains(container.getVersion()))
                        .collect(Collectors.toList());
                if(sameVersions.size() > 0){
                    finalContainers.add(hubContainer);
                }
            }
        }

        Set<ContainerImage> containerImages = new HashSet<>();
        List<String> maintainers = new ArrayList<>();
        maintainers.add("BioContainers Team <https://github.com/BioContainers>");
        List<String> containerMaintainers = (container.getMaintainer() != null && container.getMaintainer().size() >0)? container.getMaintainer()
                .stream().map(Maintainer::getMaintainername)
                .collect(Collectors.toList()): Collections.emptyList();
        maintainers.addAll(containerMaintainers);

        if(finalContainers.size() > 0){
            finalContainers.forEach(x -> x.getContainerTags().forEach(y -> {
                ContainerImage containerImage = ContainerImage.builder()
                        .size(y.getValue())
                        .fullTag(buildDockerCommand(container.getSoftwareName(), y.getKey(), accessionCommand))
                        .description(container.getDescription())
                        .containerType(ContainerType.DOCKER)
                        .lastUpdate(x.getLastUpdated())
                        .maintainer(maintainers)
                        .tag(y.getKey())
                        .build();
                containerImages.add(containerImage);
            }));
        }

        List<DockerHubContainer> finalUpdates = finalContainers.stream().filter(x -> x.getLastUpdated() != null)
                .sorted(Comparator.comparing(DockerHubContainer::getLastUpdated)).collect(Collectors.toList());

        Date finalUpdate = null;
        if(finalUpdates.stream().findFirst().isPresent())
            finalUpdate = finalContainers.stream().findFirst().get().getLastUpdated();

        if(container.getSoftwareName() == null)
            log.error("Not name for contain -- ");

        return Optional
                .of(BioContainerToolVersion
                        .builder()
                        .name(container.getSoftwareName())
                        .version(container.getSoftwareVersion())
                        .description(container.getDescription())
                        .lastUpdate(finalUpdate)
                        .hashName(GeneralUtils.getHashName(container.getSoftwareName()))
                        .containerImages(containerImages)
                        .license(container.getLicense())
                        .homeURL(container.getHomeURL())
                        .docURL(container.getDocumentationURL())
                        .license(container.getLicense())
                        .text(generateText(container.getLabels().stream().map(Label::getValue).collect(Collectors.toList())))

                        .additionalIdentifiers(container
                                .getExternalIds()
                                .entrySet()
                                .stream()
                                .map(x -> new Tuple<>(x.getKey(), x.getValue()))
                                .collect(Collectors.toList()))
                        .build());
    }

    private static String generateText(List<String> collect) {
        StringBuilder text = new StringBuilder();
        collect.forEach(x-> {
            text.append(x + " ");
        });
        return text.toString().trim();
    }

    /**
     *
     * @param softwareName Software Name
     * @param key the tag of the container image
     * @param accessionCommand Domain (empty if is dockerhub & containers.biocontainers.pro)
     * @return
     */
    private static String buildDockerCommand(String softwareName, String key, String accessionCommand) {
        StringBuilder builder = new StringBuilder();
        if(!(accessionCommand == null || accessionCommand.isEmpty()))
             builder.append(accessionCommand).append("/");

        return builder.append(softwareName).append(":")
                .append(key)
                .toString();
    }
}
