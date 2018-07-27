package pro.biocontainers.mongodb.model;

import lombok.Builder;
import lombok.Data;

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

@Data
@Builder
public class ContainerImage implements IContainerImage{

    private String accession;

    private String version;

    private String name;

    private String description;

    private Integer size;

    private String tag;

    @Override
    public String getAccession() {
        return this.accession;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }


}
