package pro.biocontainers.mongodb.model;

import lombok.Builder;
import pro.biocontainers.data.model.ContainerType;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    /** Maintainer of the Container / DockerFile **/
    private List<String> maintainer;

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

    public ContainerType getContainerType() {
        return containerType;
    }

    public List<String> getBinariesURLs() {
        return binariesURLs;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContainerImage that = (ContainerImage) o;
        return Objects.equals(accession, that.accession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accession);
    }
}
