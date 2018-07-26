package pro.biocontainers.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import pro.biocontainers.api.configs.SwaggerConfig;

@SpringBootApplication(scanBasePackageClasses = {SwaggerConfig.class})
public class ApiApp implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(ApiApp.class);

    @Override
    public void run(String... arg0) {
        logger.info("===== SERVER STARTED =======");
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(ApiApp.class).run(args);
    }

    @Component
    @Primary
    private class CustomObjectMapper extends ObjectMapper {
        public CustomObjectMapper() {
            setSerializationInclusion(JsonInclude.Include.NON_NULL);
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
            enable(SerializationFeature.INDENT_OUTPUT);

        }
    }


}
