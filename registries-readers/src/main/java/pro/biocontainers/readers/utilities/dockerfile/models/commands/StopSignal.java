package pro.biocontainers.readers.utilities.dockerfile.models.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;


@Getter
@Setter
public class StopSignal extends Instruction{
/*    @Id
    @Column(name="REPO_ID", unique=true, nullable=false)
    public long id;*/

    private long id;

    @JsonIgnore
    DockerContainer dockerContainer;

    public String signal;

    public boolean current;

    public StopSignal(DockerContainer dockerContainer, String signal) {
        super();
        this.dockerContainer = dockerContainer;
        this.signal = signal;
    }


    public StopSignal() {
    }
}
