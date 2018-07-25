package pro.biocontainers.pipelines.utilities;

import pro.biocontainers.mongodb.model.BioContainer;
import pro.biocontainers.readers.dockerhub.model.DockerHubContainer;

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

    public static BioContainer transformDockerHubContainerToBiocontainer(DockerHubContainer dockerHubContainer, String accessionURL) {
        String accession = accessionURL.replace("%%name_space", dockerHubContainer.getNameSpace()).replace("%%software_name%%", dockerHubContainer.getName());
        return  BioContainer.builder()
                .name(dockerHubContainer.getName())
                .accession(accession)
                .description(dockerHubContainer.getDescription())
                .build();

    }
}
