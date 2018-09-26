package pro.biocontainers.pipelines;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SubmissionPipeline {

  public static void main(String[] args) {
    SpringApplication.run(SubmissionPipeline.class, args);
  }
}
