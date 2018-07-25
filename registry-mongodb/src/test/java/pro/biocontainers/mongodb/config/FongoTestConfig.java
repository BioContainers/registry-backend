package pro.biocontainers.mongodb.config;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(basePackages = "pro.biocontainers.mongodb.service")
@EnableMongoRepositories(basePackages = "pro.biocontainers.mongodb.repository")
@Configuration
public class FongoTestConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "mongo-unit-test";
    }

    @Bean
    @Override
    public MongoClient mongoClient() {
        return new Fongo("mongo-test").getMongo();
    }
}
