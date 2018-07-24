package pro.biocontainers.readers.utilities.dockerfile;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.Dockerfile;
import pro.biocontainers.readers.utilities.dockerfile.models.commands.Comment;
import pro.biocontainers.readers.utilities.dockerfile.models.commands.Label;
import pro.biocontainers.readers.utilities.dockerfile.models.commands.Run;
import pro.biocontainers.readers.utilities.dockerfile.models.commands.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by salizumberi-laptop on 18.11.2016.
 */

@Getter
@Setter
public class Snapshot {

    private long id;

    private Long repoId;

    public Dockerfile getDockerfile() {
        return dockerfile;
    }

    public void setDockerfile(Dockerfile dockerfile) {
        this.dockerfile = dockerfile;
    }

    @JsonIgnore
    private Dockerfile dockerfile;

    public List<Run> runs = new ArrayList<>();

    public List<Label> labels = new ArrayList<>();

    public List<Env> envs = new ArrayList<>();

    public List<Expose> exposes = new ArrayList<>();

    public List<Add> adds = new ArrayList<>();

    public List<Copy> copies = new ArrayList<>();

    public List<Volume> volumes = new ArrayList<>();

    public List<User> users = new ArrayList<>();

    public List<WorkDir> workDirs = new ArrayList<>();

    public List<Arg> args = new ArrayList<>();

    public List<Comment> comments = new ArrayList<>();

    public List<OnBuild> onBuilds = new ArrayList<>();

    public Healthcheck healthCheck;

    public EntryPoint entryPoint;

    public From from;




    public Cmd cmd;

    public StopSignal stopSignals;

    public Maintainer maintainer;

    private int instructions;

    private long commitDate;

    private long fromDate;

    public Long getRepoId() {
        return repoId;
    }

    public void setRepoId(Long repoId) {
        this.repoId = repoId;
    }

    private long toDate;

    private String changeType;

    private int del;

    private int ins;

    private Boolean imageIsAutomated;

    private Boolean imageIsOffical;

    private Integer starCount;

    private int index;

    private boolean isCurrentDockerfile;

    public int countInstructions() {
        int counter = 1;
        if (maintainer != null) {
            counter++;
        }
        if (cmd != null) {
            counter++;
        }
        if (entryPoint != null) {
            counter++;
        }
        if (stopSignals != null) {
            counter++;
        }
        if (healthCheck != null) {
            counter++;
        }
        return runs.size() +
                labels.size() +
                envs.size() +
                exposes.size() +
                adds.size() +
                copies.size() +
                volumes.size() +
                users.size() +
                workDirs.size() +
                args.size() +
                onBuilds.size() + counter;
    }

    public Snapshot() {

    }
}
