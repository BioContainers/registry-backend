package pro.biocontainers.readers.utilities.conda;

import org.junit.Test;

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
public class CondaRecipeReaderTest {

    @Test
    public void parse() throws URISyntaxException, IOException {
        File file = new File(Objects.requireNonNull(CondaRecipeReaderTest.class.getClassLoader().getResource("files/meta.yaml")).toURI());
        CondaRecipeReader.parseProperties(file);

    }


}