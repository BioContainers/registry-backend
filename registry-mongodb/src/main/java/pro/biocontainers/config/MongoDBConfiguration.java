package pro.biocontainers.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = "pro.biocontainers.service")
@EnableMongoRepositories(basePackages = "pro.biocontainers.repository.")
public class MongoDBConfiguration  extends AbstractMongoConfiguration {


    @Value("${mongodb.biocontainers.db.database}")
    private String database;

    @Value("${mongodb.biocontainers.db.user}")
    private String user;

    @Value("${mongodb.biocontainers.db.password}")
    private String password;

    @Value("${mongodb.biocontainers.db.authenticationDatabase}")
    private String authenticationDatabse;

    @Value("${mongodb.biocontainers.db.port}")
    private String port;

    @Value("${maven.biocontainers.db.host}")
    private String mongoHost;


    @Bean
    @Override
    public MongoClient mongoClient() {
        MongoCredential credential = MongoCredential.createCredential(getUser(), getAuthenticationDatabse(), getPassword().toCharArray());
        MongoClientOptions options = MongoClientOptions.builder().build();
        MongoClient mongoClient = configureSingleMachine(credential, options);
        return mongoClient;
    }

    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(mongoDbFactory());
    }

    @Bean
    public MongoDbFactory mongoDbFactory(){
        return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
    }


    /**
     * Create machine in single mode run, for example for localhost.
     * @param credential Credentials
     * @param options options
     * @return MongoClient.
     */
    private MongoClient configureSingleMachine(MongoCredential credential, MongoClientOptions options) {
        ServerAddress serverAddress = new ServerAddress(getMongoHost(), Integer.parseInt(getPort()));
        if(credential != null)
            return new MongoClient(serverAddress, credential, options);
        return new MongoClient(serverAddress, options);
    }

    public String getUser(){
        return this.user;
    }

    public  String getPassword(){
        return this.password;
    }

    public String getAuthenticationDatabse(){
        return this.authenticationDatabse;
    }


    public String getPort(){
        return this.port;
    }

   public String getMongoHost(){
        return this.mongoHost;
   }

    @Override
    protected String getDatabaseName() {
        return this.database;
    }
}