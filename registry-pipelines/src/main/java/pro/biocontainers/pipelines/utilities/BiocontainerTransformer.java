package pro.biocontainers.pipelines.utilities;

import pro.biocontainers.mongodb.model.BioContainer;
import pro.biocontainers.mongodb.model.ContainerImage;
import pro.biocontainers.mongodb.model.Tuple;
import pro.biocontainers.readers.IRegistryContainer;
import pro.biocontainers.readers.dockerhub.model.DockerHubContainer;
import pro.biocontainers.readers.quayio.model.QuayIOContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static BioContainer transformContainerToBiocontainer(IRegistryContainer container, String accessionURL) {
        String accession = accessionURL.replace("%%name_space%%", container.getNameSpace()).replace("%%software_name%%", container.getName());
        List<ContainerImage> images = new ArrayList<>();
        container.getContainerTags().stream().forEach( x-> {
            images.add(ContainerImage.builder()
                    .size(x.getValue())
                    .tag(x.getKey())
                    .build());
        });
        return  BioContainer.builder()
                .name(container.getName())
                .accession(accession)
                .description(container.getDescription())
                .lastUpdate(container.getLastUpdated())
                .pullCount(container.getPullCount())
                .images(images)
                .starred(container.isStarred())
                .build();

    }
}
