package pro.biocontainers.readers.utilities.dockerfile.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import pro.biocontainers.readers.utilities.dockerfile.DockerLinter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Dockerfile {

    private long id;

    @JsonIgnore
    private Project project;

    private long repo_id;
    private String localRepoPath;
    private String dockerPath;
    private long firstCommitDate;

    private long firstDockerCommitDate;

    private int size;

    private int commits;

    private List<DockerContainer> dockerfileDockerContainers = new ArrayList<>();

    private List<String> violatedRules = new ArrayList<>();

    public int getCommits() {
        return dockerfileDockerContainers.size();
    }

    public List<String> getViolatedRules() throws IOException, InterruptedException {
        List<String> rules = new ArrayList<>();
        File dockerfile = new File(localRepoPath + "/" + dockerPath);
        String violations = DockerLinter.getReportOfLinting(dockerfile);
        String[] lines = StringUtils.split(violations, "\r\n");
        for (String l : lines) {
            String[] parts = l.split(" ");
            if (parts[1].contains("unexpected")) {
                rules.add(l);
            } else {
                rules.add(parts[1]);
            }
        }
        this.violatedRules = rules;
        return rules;
    }

    public Dockerfile() {

    }

}
