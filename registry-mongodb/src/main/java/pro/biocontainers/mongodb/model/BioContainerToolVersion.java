package pro.biocontainers.mongodb.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pro.biocontainers.data.model.DescriptorType;
import pro.biocontainers.data.model.ToolDescriptor;
import pro.biocontainers.data.model.ToolVersion;
import pro.biocontainers.data.model.Tuple;

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
 * Created by ypriverol (ypriverol@gmail.com) on 27/07/2018.
 */
@Document(collection = "BioContainerToolVersion")
@Builder
@CompoundIndex(def = "{'name':1, 'version':1}", name = "name_version")
public class BioContainerToolVersion implements ToolVersion {

    @Id
    @Field("uui")
    String uui;

    @Field("version")
    String version;

    @Indexed(name = "name")
    String name;

    @Field("description")
    String description;

    /** Main URL where the user can download the container Tool. **/
    @Field("homeURL")
    String homeURL;

    /** Main URL where the user can download the container Tool. **/
    @Field("docURL")
    String docURL;

    @Field("license")
    String license;

    @Field("additionalIdentifiers")
    List<Tuple<String, List<String>>> additionalIdentifiers;

    /** Container Images **/
    @Field("containerImages")
    private Set<ContainerImage> containerImages;

    /** Descriptor Types **/
    private List<DescriptorType> descriptorTypes;

    /** Descriptors **/
    @Field("descriptors")
    private List<ToolDescriptor> descriptors;

    @Indexed(name = "hashName")
    private String hashName;

    @Indexed(name = "lastUpdate")
    Date lastUpdate;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getId() {
        return this.version;
    }

    @Override
    public String getLatestImage() {
        List<ContainerImage> images = getImages().stream().sorted(Comparator.comparing(ContainerImage::getLastUpdate)).collect(Collectors.toList());
        ContainerImage image = images.stream().findFirst().get();
        return image.getAccession();
    }

    @Override
    public Collection<? extends ContainerImage> getImages() {
        return this.containerImages;
    }

    @Override
    public List<DescriptorType> getDescriptorTypes() {
        return this.descriptorTypes;
    }

    @Override
    public Boolean getContainerFile() {
        return null;
    }

    @Override
    public String getMetaVersion() {
        return this.version;
    }

    @Override
    public String getHashName() {
        return this.hashName;
    }
}
