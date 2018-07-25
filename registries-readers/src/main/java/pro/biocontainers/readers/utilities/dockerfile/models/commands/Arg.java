package pro.biocontainers.readers.utilities.dockerfile.models.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

@Getter
@Setter
public class Arg extends Instruction{
    public long id;


    @JsonIgnore
    DockerContainer dockerContainer;
    public String arg;
    public boolean current;

    public Arg(DockerContainer dockerContainer, String arg) {
        super();
        this.dockerContainer = dockerContainer;
        this.arg = arg;
    }

    public Arg() {
    }
}
