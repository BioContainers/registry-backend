package pro.biocontainers.readers.github.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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
 * Created by ypriverol (ypriverol@gmail.com) on 26/07/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GitHubFileNameList {

    String sha;
    String url;
    List<GitHubFileName> tree;

    public GitHubFileNameList() {
    }
}
