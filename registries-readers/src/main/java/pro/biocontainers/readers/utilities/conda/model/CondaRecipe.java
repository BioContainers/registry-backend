package pro.biocontainers.readers.utilities.conda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pro.biocontainers.readers.ExternalID;
import pro.biocontainers.readers.IContainerRecipe;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
public class CondaRecipe implements IContainerRecipe {

    private static final String VERSION = "version";
    private static final String NAME = "name";
    private static final String URL = "url";
    private static final String HOME = "home";
    private static final String SUMMARY = "summary";
    private static final String LICENSE = "license";
    private static final String DOC_URL="doc_url";
    private static final String IDENTIFIERS = "identifiers";

    @JsonProperty(value = "package")
    Map<String,String> recipeProperties;

    @JsonProperty(value = "requirements")
    Map<String, List<String>> requirements;

    @JsonProperty(value = "tests")
    Map<String, List<String>> tests;

    @JsonProperty(value = "about")
    Map<String, String> about;

    @JsonProperty("extra")
    Map<String, Object> extras = new ConcurrentHashMap<>();

    @JsonProperty(value = "source")
    Map<String, Object> sources = new ConcurrentHashMap<>();

    /** Version of the software **/
    private String softwareVersion;

    /** Parse the name of the Container*/
    String softwareName;

    /** Where the binary can be found **/
    String binaryURL;

    String homeURL;
    private String description;

    private String license;
    private String docURL;
    private Map<String, List<String>> identifiers = new ConcurrentHashMap<>();

    public Map<String, Object> getExtras() {
        return extras;
    }

    public void parseProperties(){
        if(recipeProperties.containsKey(NAME))
            this.softwareName = recipeProperties.get(NAME);

        if(recipeProperties.containsKey(VERSION))
            this.softwareVersion = recipeProperties.get(VERSION);

        if(sources != null && sources.containsKey(URL)){
            if(sources.get(URL) instanceof String){
                this.binaryURL = (String) sources.get(URL);
            }else if(sources.get(URL) instanceof List){
                this.binaryURL = (String) ((List) sources.get(URL)).get(0);
            }

        }

        if(about != null){
            if(about.containsKey(HOME))
                this.homeURL = about.get(HOME);

            if(about.containsKey(SUMMARY))
                this.description = about.get(SUMMARY);

            if(about.containsKey(LICENSE))
                this.license = about.get(LICENSE);

            if(about.containsKey(DOC_URL))
                this.docURL = about.get(DOC_URL);
        }


        if(extras != null && extras.containsKey(IDENTIFIERS)){
            this.identifiers = new HashMap<>();
            List<String> identifiers = (List<String>) extras.get(IDENTIFIERS);
            identifiers.forEach(x-> {
                Optional<ExternalID> externalID = ExternalID.findValue(x.split(":")[0]);
                if(externalID.isPresent()){
                    List<String> values = new ArrayList<>();
                    if(this.identifiers.containsKey(externalID.get().name()))
                        values = this.identifiers.get(externalID.get().name());
                    values.add(x.split(":")[1]);
                    this.identifiers.put(externalID.get().name(), values);
                }
            });
        }
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getLicense() {
        return this.license;
    }

    @Override
    public String getDocumentationURL() {
        return this.docURL;
    }

    @Override
    public Map<String, List<String>> getExternalIds() {
        return this.identifiers;
    }

    @Override
    public String getSoftwareVersion() {
        return this.softwareVersion;
    }
}
