package pro.biocontainers.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Contact details of Biocontainer API service team")
public class Contact {

    @ApiModelProperty(value = "name")
    private final String name;

    @ApiModelProperty(value = "URL")
    private final String url;

    @ApiModelProperty(value = "email")
    private final String email;
}
