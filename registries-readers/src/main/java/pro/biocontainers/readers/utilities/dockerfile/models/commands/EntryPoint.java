package pro.biocontainers.readers.utilities.dockerfile.models.commands;

import lombok.Getter;
import lombok.Setter;

import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

import java.util.List;

@Getter
@Setter
public class EntryPoint extends Instruction {

    private long id;

    DockerContainer dockerContainer;

    public String executable;

    public List<String> params;

    public boolean current;

    public String allParams;

    public EntryPoint(DockerContainer dockerContainer, String executable, List<String> params) {
        super();
        this.dockerContainer = dockerContainer;
        this.executable = executable;
        this.params = params;

        StringBuilder allParams = new StringBuilder();
        for (String p : params) {
            allParams.append("¦").append(p);
        }
        if (allParams.length() > 240) {
            this.allParams = allParams.substring(0, 240) + "...";
        } else {
            this.allParams = allParams.toString();
        }
    }

    public EntryPoint() {
    }
}
