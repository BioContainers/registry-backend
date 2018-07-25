package pro.biocontainers.readers.utilities.dockerfile.models.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;



@Getter
@Setter
public class WorkDir extends Instruction{
   public long id;

    @JsonIgnore
    DockerContainer dockerContainer;

    public String path;

    public boolean current;

    public WorkDir(DockerContainer dockerContainer, String path) {
        super();
        this.dockerContainer = dockerContainer;
        this.path= path;
    }

    public WorkDir() {
    }
}
