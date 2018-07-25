package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;


@Getter
@Setter
public class Healthcheck extends Instruction {

    private long id;

    @JsonIgnore
    DockerContainer dockerContainer;

    String optionsBeforeInstructions;

    public boolean current;

    public String instruction;

    public String allParams;

    public Healthcheck(DockerContainer dockerContainer, String instruction, String allParams) {
        super();
        this.dockerContainer = dockerContainer;
        this.instruction = instruction;
        this.allParams = allParams;
    }

    public Healthcheck(String optionsBeforeInstructions) {
        super();
        this.optionsBeforeInstructions = optionsBeforeInstructions;
    }

    public Healthcheck(DockerContainer dockerContainer, String instruction, String optionsBeforeInstructions, String allParams) {
        super();
        this.dockerContainer = dockerContainer;
        this.instruction = instruction;
        this.optionsBeforeInstructions = optionsBeforeInstructions;

        if (allParams.length() > 240) {
            this.allParams = allParams.substring(0, 240) + "...";
        } else {
            this.allParams = allParams;
        }
    }


    public Healthcheck() {
    }
}
