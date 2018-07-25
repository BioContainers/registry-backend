package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;



@Setter
@Getter
public class Volume extends Instruction{
    public long id;


    @JsonIgnore
    DockerContainer dockerContainer;

    public String value;

    public boolean current;

    public Volume(DockerContainer dockerContainer, String match) {
        super();
        this.dockerContainer = dockerContainer;
        this.value = match;
    }

    public Volume() {
    }
}
