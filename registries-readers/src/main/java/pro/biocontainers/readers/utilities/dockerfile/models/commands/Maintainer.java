package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

@Getter
@Setter
public class Maintainer extends Instruction{
/*    @Id
    @Column(name="REPO_ID", unique=true, nullable=false)
    public long id;*/

    private long id;

    @JsonIgnore
    DockerContainer dockerContainer;

    public String maintainername;

    public boolean current;

    public Maintainer(DockerContainer dockerContainer, String maintainername) {
        super();
        this.dockerContainer = dockerContainer;
        this.maintainername  =maintainername;
    }

    public Maintainer() {
    }
}
