package pro.biocontainers.api.configs;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.biocontainers.api.model.Metadata;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

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
 * Created by ypriverol (ypriverol@gmail.com) on 14/06/2018.
 */

@Configuration
public class SwaggerConfig {

    private Metadata metadata;

    @Bean
    Metadata getMetadata() {
        this.metadata = Metadata.builder()
                .apiVersion("v2")
                .country("Europe")
                .friendlyName("BioContainers RestFul API")
                .version("v2")
                .description("The BioContainers Restful API provides the information of all the containers and tools develop by BioContainers Community -- http://biocontainers.pro")
                .contact(pro.biocontainers.api.model.Contact.builder()
                        .email("biocotainers@gmail.com")
                        .name("BioContainers Community")
                        .url("http://biocontainers.pro")
                        .build())
                .license("Apache License Version 2.0")
                .build();
        return this.metadata;
    }


    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pro.biocontainers.api.controller"))
                .paths(paths())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * This function exclude all the paths we don't want to show in the swagger documentation.
     * @return List of paths
     */
    private Predicate<String> paths() {
        return Predicates.not(PathSelectors.regex("/error"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(metadata.getFriendlyName())
                .description(metadata.getDescription())
                .contact(new Contact(metadata.getContact().getName(), metadata.getContact().getUrl(), metadata.getContact().getEmail()))
                .license(metadata.getLicense())
                .version(metadata.getVersion())
                .build();
    }
}
