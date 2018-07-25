package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;


@Getter
@Setter
public class Expose extends Instruction{
    public long id;

    @JsonIgnore
    DockerContainer dockerContainer;

    public long port;

    public boolean current;

    public Expose(DockerContainer dockerContainer, String port) {
        super();
        this.dockerContainer = dockerContainer;
        String p = port.replaceAll("[^0-9]+", " ");
        p = p.replaceAll(" ", "");
        this.port = Integer.parseInt(p);
}

    public Expose() {
    }
}
