package pro.biocontainers.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * Created by ypriverol (ypriverol@gmail.com) on 31/07/2018.
 */

@Data
@Builder
@ApiModel(description = "A Container Image is a container version of an specific Software tool Version for example: comet:1.0-deb_cv or comet:1.0--python ")
public class ContainerImage {

    @ApiModelProperty(value = "A unique identifier for the container image for the software version")
    @JsonProperty("accession")
    private String accession;

    @ApiModelProperty(value = "Tag version in the container registry")
    @JsonProperty("tag")
    private String tag;

    @ApiModelProperty(value = "Size of the container in the registry")
    @JsonProperty("size")
    private Integer size;

    @ApiModelProperty(value = "Number of downloads for of the image")
    @JsonProperty("downloads")
    private Integer downloads;

    @ApiModelProperty(value = "Last update of the container image")
    @JsonProperty("last_update")
    private Date lastUpdate;

    @JsonProperty("containerfile")
    @ApiModelProperty(value = "Reports if this tool has a containerfile available.")
    private Boolean containerfile;

    @JsonProperty("url_recipe")
    @ApiModelProperty(value = "Public URL of the recipe")
    private Boolean urlRecipe;

    @JsonProperty("url_readable_recipe")
    @ApiModelProperty(value = "Public URL of the recipe")
    private Boolean urlReadableRecipe;

}
