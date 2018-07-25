package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;


@Getter
@Setter
public class OnBuild extends Instruction{

    public long id;

    @JsonIgnore
    DockerContainer dockerContainer;

    public boolean current;

    public String instruction;

    public String allParams;

    public OnBuild(DockerContainer dockerContainer, String instruction, String allParams) {
        super();
        this.dockerContainer = dockerContainer;
        this.instruction =instruction;

        if (allParams.length() > 240){
            this.allParams = allParams.substring(0, 240) + "...";
        }else{
            this.allParams= allParams;
        }
    }

    public OnBuild() {

    }
}
