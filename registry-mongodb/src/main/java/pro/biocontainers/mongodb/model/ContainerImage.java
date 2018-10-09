package pro.biocontainers.mongodb.model;

import lombok.Builder;
import pro.biocontainers.data.model.ContainerType;
import pro.biocontainers.data.model.ToolContainerfile;

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
 * This Container Image
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 26/07/2018.
 */

@Builder
public class ContainerImage implements IContainerImage{

    /** Tag **/
    private String tag;

    /** Full Tag quay.io/biocontainers/abaca:1.2--python **/
    String fullTag;

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

    /** Recipe URL (it can be the folder that container all scripts, etc)**/
    private String containerFileProject;

    /** Public Readable Recipe for Conda or DockerFile **/
    private ContainerFile containerFile;

    /** License for the software use **/
    private String license;

    /** Software home URL **/
    private String softwareURL;

    /** Software Documentation web pages **/
    private String documentationURL;

    /** Additional Metadata **/
    private String additionalMetadata;

    @Override
    public String getId() {
        return this.tag;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getTag() {
        return this.tag;
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
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }

    public ContainerFile getContainerFile() {
        return containerFile;
    }

    public void setContainerFile(ContainerFile containerFile) {
        this.containerFile = containerFile;
    }

    public List<String> getMaintainer() {
        return maintainer;
    }

    @Override
    public String getFullTag() {
        return fullTag;
    }

    public String getContainerFileProject() {
        return containerFileProject;
    }

    public String getLicense() {
        return license;
    }

    public String getSoftwareURL() {
        return softwareURL;
    }

    public String getDocumentationURL() {
        return documentationURL;
    }

    public String getAdditionalMetadata() {
        return additionalMetadata;
    }
}
