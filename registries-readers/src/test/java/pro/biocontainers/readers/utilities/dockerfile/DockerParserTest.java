package pro.biocontainers.readers.utilities.dockerfile;

import org.junit.Assert;
import org.junit.Test;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

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
public class DockerParserTest {

    @Test
    public void getParsedDockerfileObject() throws IOException, URISyntaxException {
        File rawDockerfile1 = new File( Objects.requireNonNull(DockerParserTest.class.getClassLoader().getResource("files/Dockerfile")).toURI());
        DockerParser parser = new DockerParser(rawDockerfile1.getParent(), rawDockerfile1.getAbsolutePath());
        DockerContainer dockerfileDockerContainer1 = parser.getParsedDockerfileObject(rawDockerfile1);
        Assert.assertEquals("Number of labels is 10", 11, dockerfileDockerContainer1.labels.size());
        Assert.assertTrue("The version of the software is -- 1.3.1-3-deb", dockerfileDockerContainer1.getSoftwareVersion().equalsIgnoreCase("1.3.1-3-deb"));

        File rawDockerfile2 = new File( Objects.requireNonNull(DockerParserTest.class.getClassLoader().getResource("files/coregenebuilder/Dockerfile")).toURI());
        parser = new DockerParser(rawDockerfile2.getParent(), rawDockerfile2.getAbsolutePath());
        DockerContainer dockerfileDockerContainer2 = parser.getParsedDockerfileObject(rawDockerfile2);
        Assert.assertEquals("Number of labels is 10", 14, dockerfileDockerContainer2.labels.size());
        Assert.assertTrue("The version of the software is -- 1.41", dockerfileDockerContainer2.getSoftwareVersion().equalsIgnoreCase("1.0"));
        Assert.assertTrue("The identifiers in bio.tools -- ", dockerfileDockerContainer2.getExternalIds().size() == 1);
        Assert.assertTrue("The maintainer number is 1 ", dockerfileDockerContainer2.getMaintainer().size() == 1);
    }
}