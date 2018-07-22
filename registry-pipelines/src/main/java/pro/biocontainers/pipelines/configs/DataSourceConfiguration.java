package pro.biocontainers.pipelines.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/batch-h2.properties")
public class DataSourceConfiguration {

    @Autowired
    private Environment environment;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    protected void initialize() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(resourceLoader.getResource(environment.getProperty("batch.schema.script")));
        populator.setContinueOnError(true);
        DatabasePopulatorUtils.execute(populator , dataSource());
    }

    @Bean(name = "hsqlDatraSource", destroyMethod="close")
    @Primary
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("batch.jdbc.driver"));
        dataSource.setUrl(environment.getProperty("batch.jdbc.url"));
        dataSource.setUsername(environment.getProperty("batch.jdbc.user"));
        dataSource.setPassword(environment.getProperty("batch.jdbc.password"));
        return dataSource;
    }

}