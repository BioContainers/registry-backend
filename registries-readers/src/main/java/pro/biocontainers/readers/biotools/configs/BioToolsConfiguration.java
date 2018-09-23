package pro.biocontainers.readers.biotools.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource(value = {"classpath:application.properties"})
public class BioToolsConfiguration {

    @Value("${biotools.api.urls}")
    private String biotoolsAPIURL;

}
