package pro.biocontainers.readers.utilities.conda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

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
 * Created by ypriverol (ypriverol@gmail.com) on 24/07/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CondaRecipe {

    @JsonProperty(value = "package")
    Map<String,String> pageProperties;

    @JsonProperty(value = "requirements")
    Map<String, List<String>> requirements;

    @JsonProperty(value = "tests")
    Map<String, List<String>> tests;

    @JsonProperty(value = "about")
    Map<String, String> about;

    @JsonProperty("extra")
    Map<String, Object> extras;



}
