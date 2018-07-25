package pro.biocontainers.readers.utilities.dockerfile.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.ExternalID;
import pro.biocontainers.readers.utilities.IContainerRecipe;
import pro.biocontainers.readers.utilities.dockerfile.models.commands.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class DockerContainer implements IContainerRecipe {

    private static final String SOFTWARE = "software";
    private static final String DESCRIPTION = "about.summary";
    private static final String LICENSE = "about.license";
    private static final String HOME_URL = "about.home";
    private static final String DOC_URL = "about.documentation";
    private static final String SOFTWARE_VERSION = "software.version";


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


    public DockerContainer() {

    }

    @Override
    public String getSoftwareName() {
        return getLabelValue(SOFTWARE);
    }

    @Override
    public String getDescription() {
        return getLabelValue(DESCRIPTION);
    }

    @Override
    public String getLicense() {
        return getLabelValue(LICENSE);
    }

    @Override
    public String getHomeURL() {
        return getLabelValue(HOME_URL);
    }

    @Override
    public String getDocumentationURL() {
        return getLabelValue(DOC_URL);
    }

    @Override
    public Map<String, List<String>> getExternalIds() {
        Map<String, List<String>> identifiers = new HashMap<>();
        if(labels != null){
            for(Label label: labels){
                for(ExternalID extId: ExternalID.values()){
                    if(label.key.contains(extId.name())){
                        List<String> values = (identifiers.containsKey(extId.name())) ?identifiers.get(extId.name()):new ArrayList<>();
                        values.add(label.value);
                        identifiers.put(extId.name(), values);
                    }
                }
            }
        }
        return identifiers;
    }

    @Override
    public String softwareVersion() {
        return getLabelValue(SOFTWARE_VERSION);
    }

    /**
     * Get the value for an specific Key
     * @param key {@link Label} key
     * @return value
     */
    private String getLabelValue(String key){
        String value = null;
        if(labels != null && labels.size() > 0){
            for(Label label: labels){
                if(label.key.equalsIgnoreCase(key))
                    value = label.value;
            }
        }
        return value;
    }
}
