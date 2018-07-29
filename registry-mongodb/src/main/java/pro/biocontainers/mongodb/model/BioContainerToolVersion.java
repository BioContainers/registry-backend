package pro.biocontainers.mongodb.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pro.biocontainers.data.model.DescriptorType;
import pro.biocontainers.data.model.ToolDescriptor;
import pro.biocontainers.data.model.ToolVersion;

import java.util.Collection;
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
 * Created by ypriverol (ypriverol@gmail.com) on 27/07/2018.
 */
@Document(collection = "BioContainerToolVersion")
@Builder
public class BioContainerToolVersion implements ToolVersion {

    @Id
    String id;

    @Indexed(name = "name")
    String name;

    @Field("description")
    String description;

    /** Main URL where the user can download the container Tool. **/
    @Indexed(name = "url")
    String url;

    /** Used in conjunction with a registry_url if provided to locate images **/
    @Indexed(name = "imageId")
    private String imageId;

    @Indexed(name = "registryURL")
    private String registryURL;

    /** Container Images **/
    @Indexed(name = "containerImages")
    private List<ContainerImage> containerImages;

    /** Descriptor Types **/
    private List<DescriptorType> descriptorTypes;

    /** Descriptors **/
    @Field("descriptors")
    private List<ToolDescriptor> descriptors;

    private Boolean isContainerRecipeAvailable;

    @Field("urlRecipe")
    private String urlRecipe;

    @Field("version")
    private String version;

    private Boolean isVerified;

    private String verifiedSource;

    @Indexed(name = "hashName")
    private String hashName;

    @Indexed(name = "lastUpdate")
    Date lastUpdate;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public String getId() {
        return this.id;
    }

    /** Image in a registry **/
    @Override
    public String getImage() {
        return this.imageId;
    }

    @Override
    public String getRegistryUrl() {
        return this.registryURL;
    }

    @Override
    public Collection<? extends ContainerImage> getImageName() {
        return this.containerImages;
    }

    @Override
    public List<DescriptorType> getDescriptorTypes() {
        return this.descriptorTypes;
    }

    @Override
    public Boolean getContainerFile() {
        return this.isContainerRecipeAvailable;
    }

    @Override
    public String getMetaVersion() {
        return this.version;
    }

    @Override
    public Boolean isVerified() {
        return this.isVerified;
    }

    @Override
    public String getVerifiedSource() {
        return this.verifiedSource;
    }

    @Override
    public String getHashName() {
        return this.hashName;
    }
}
