package pro.biocontainers.mongodb.model;

import lombok.Builder;
import lombok.Data;
import pro.biocontainers.data.model.ContainerType;

import java.util.Date;
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
 * Created by ypriverol (ypriverol@gmail.com) on 26/07/2018.
 */


@Builder
public class ContainerImage implements IContainerImage{

    /** This is the Container Tag in Conda or BioContainers to be added**/
    private String accession;

    /** Tag **/
    private String tag;

    /** Container Type **/
    private ContainerType containerType;

    /** List of binaries URLs **/
    private List<String> binariesURLs;

    /** Description of the Container Image **/
    private String description;

    /** Size **/
    private Integer size;

    /** Number of downloads **/
    private Integer downloads;

    private Date lastUpdate;

    @Override
    public String getAccession() {
        return this.accession;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getTag() {
        return this.accession;
    }

    @Override
    public Integer getSize() {
        return this.size;
    }
}
