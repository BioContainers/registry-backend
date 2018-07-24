package pro.biocontainers.readers.utilities.conda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

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
@Data
public class CondaRecipe {

    private static final String VERSION = "version";
    private static final String NAME = "name";
    private static final String URL = "url";
    private static final String HOME = "home";

    @JsonProperty(value = "package")
    Map<String,String> recipeProperties;

    @JsonProperty(value = "requirements")
    Map<String, List<String>> requirements;

    @JsonProperty(value = "tests")
    Map<String, List<String>> tests;

    @JsonProperty(value = "about")
    Map<String, String> about;

    @JsonProperty("extra")
    Map<String, Object> extras;

    @JsonProperty(value = "source")
    Map<String, String> sources;

    /** Version of the software **/
    private String version;

    /** Parse the name of the Container*/
    String name;

    /** Where the binary can be found **/
    String binaryURL;

    String homeURL;

    public Map<String, Object> getExtras() {
        return extras;
    }

    public void parseProperties(){
        if(recipeProperties.containsKey(NAME))
            this.name = recipeProperties.get(NAME);

        if(recipeProperties.containsKey(VERSION))
            this.version = recipeProperties.get(VERSION);

        if(sources.containsKey(URL))
            this.binaryURL = sources.get(URL);

        if(about.containsKey(HOME))
            this.homeURL = about.get(HOME);


    }
}
