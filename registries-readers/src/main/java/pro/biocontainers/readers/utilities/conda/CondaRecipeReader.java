package pro.biocontainers.readers.utilities.conda;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.RenderResult;
import lombok.extern.log4j.Log4j;
import pro.biocontainers.readers.utilities.conda.model.CondaRecipe;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
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
@Log4j
public class CondaRecipeReader {

    public static CondaRecipe parseProperties(File file) throws IOException {

        Jinjava jinjava = new Jinjava();
        String template = readFile(file, Charset.defaultCharset());
        Map<String, Object> context = Maps.newHashMap();

        RenderResult result = jinjava.renderForResult(template, context);
        log.info(result.getContext().toString());

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        CondaRecipe recipe = mapper.readValue(result.getOutput(), CondaRecipe.class);
        recipe.parseProperties();


        return recipe;
    }

    static String readFile(File file, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded, encoding);
    }


}
