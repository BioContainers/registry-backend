package pro.biocontainers.readers;

import pro.biocontainers.data.model.Tuple;

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
 * Created by ypriverol (ypriverol@gmail.com) on 25/07/2018.
 */
public interface IRegistryContainer {

    String getNameSpace();

    String getName();

    Boolean isPrivate();

    Integer getPullCount();

    Date getLastUpdated();

    Boolean isStarred();

    Integer getStartCount();

    String getDescription();

    List<Tuple<String, Integer>> getContainerTags();

    List<Tuple<Date, Integer>> getContainerStats();

}
