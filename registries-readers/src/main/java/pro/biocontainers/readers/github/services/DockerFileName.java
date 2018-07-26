package pro.biocontainers.readers.github.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerFileName {

    @JsonProperty(value = "path")
    String path;
    @JsonProperty(value = "mode")
    String mode;
    @JsonProperty(value = "type")
    String type;
    @JsonProperty(value = "sha")
    String sha;
    @JsonProperty(value = "size")
    String size;
    @JsonProperty( value = "url")
    String url;

    public DockerFileName() {
    }

    public String getPath() {
        return path;
    }

    public String getMode() {
        return mode;
    }

    public String getType() {
        return type;
    }

    public String getSha() {
        return sha;
    }

    public String getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }
}
