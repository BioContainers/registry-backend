package pro.biocontainers.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ApiApp implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(ApiApp.class);

    @Override
    public void run(String... arg0) {
        logger.info("===== SERVER STARTED =======");
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(ApiApp.class).run(args);
    }

}
