package pro.biocontainers.mongodb.model;

import pro.biocontainers.data.model.ContainerImage;

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
public interface IContainerImage extends ContainerImage {

    public String getAccession();

    public String getVersion();

    public String getDescription();

}
