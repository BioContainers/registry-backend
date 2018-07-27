package pro.biocontainers.pipelines.utilities;

import pro.biocontainers.mongodb.model.BioContainerTool;
import pro.biocontainers.mongodb.model.BioContainerToolVersion;
import pro.biocontainers.mongodb.model.ContainerImage;
import pro.biocontainers.readers.IRegistryContainer;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

import java.util.ArrayList;
import java.util.List;

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
        String accession = accessionURL.replace("%%name_space%%", container.getNameSpace()).replace("%%software_name%%", container.getName());
        List<ContainerImage> images = new ArrayList<>();
        container.getContainerTags().stream().forEach( x-> {
            images.add(ContainerImage.builder()
                    .size(x.getValue())
                    .tag(x.getKey())
                    .build());
        });
        return  BioContainerTool.builder()
                .name(container.getName())
                .id(accession)
                .description(container.getDescription())
//                .lastUpdate(container.getLastUpdated())
//                .pullCount(container.getPullCount())
//                .images(images)
                .starred(container.isStarred())
                .build();

    }

    /**
     * Convert Docker Container to {@link BioContainerToolVersion}
     * @param container {@link DockerContainer}
     * @param accessionURL url
     * @return BioContainerToolVersion
     */
    public static BioContainerToolVersion transformContainerToolVerionToBiocontainer(DockerContainer container, String accessionURL) {
        return BioContainerToolVersion
                .builder()
                .name(container.getSoftwareName())
                .build();
    }
}
