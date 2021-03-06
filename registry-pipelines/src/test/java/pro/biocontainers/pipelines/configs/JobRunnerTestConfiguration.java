package pro.biocontainers.pipelines.configs;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to setup the helper JobLauncherTestUtils for unit testing jobs and
 * steps.
 */

@Configuration
@EnableAutoConfiguration
public class JobRunnerTestConfiguration {

  @Autowired
  JobLauncher jobLauncher;

  @Autowired
  JobRepository jobRepository;

  /**
   * Sets up the JobLauncherTestUtils for uniting jobs and steps.
   *
   * @return the JobLauncherTestUtils
   */
  @Bean
  public JobLauncherTestUtils utils() {
    JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();

    jobLauncherTestUtils.setJobLauncher(jobLauncher);
    jobLauncherTestUtils.setJobRepository(jobRepository);

    return jobLauncherTestUtils;
  }
}
