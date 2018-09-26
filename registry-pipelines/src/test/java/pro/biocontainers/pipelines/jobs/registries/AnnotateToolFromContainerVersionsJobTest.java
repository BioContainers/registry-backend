package pro.biocontainers.pipelines.jobs.registries;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pro.biocontainers.pipelines.configs.JobRunnerTestConfiguration;
import pro.biocontainers.pipelines.jobs.AnnotateToolFromContainerVersionsJob;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AnnotateToolFromContainerVersionsJob.class, JobRunnerTestConfiguration.class})
@TestPropertySource(value = "classpath:application-test.properties")
@Slf4j
public class AnnotateToolFromContainerVersionsJobTest {

    @Autowired
    AnnotateToolFromContainerVersionsJob importJob;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    /**
     * This test should be run during the development process
     * @throws Exception
     */
    @Test
    public void importFromRegistries() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Assert.assertEquals(BatchStatus.COMPLETED.name(), jobExecution.getExitStatus().getExitCode());
    }
}