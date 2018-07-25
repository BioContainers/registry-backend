package pro.biocontainers.readers.utilities.dockerfile.models.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;


@Getter
@Setter
public class User extends Instruction{
    public long id;

    @JsonIgnore
    DockerContainer dockerContainer;

    public String username;

    public boolean current;

    public User(DockerContainer dockerContainer, String username) {
        super();
        this.dockerContainer = dockerContainer;
        this.username = username;
    }

    public User() {
    }
}
