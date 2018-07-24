package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.Snapshot;

/**
 * Created by salizumberi-laptop on 01.11.2016.
 */
@Getter
@Setter
public class Maintainer extends Instruction{
/*    @Id
    @Column(name="REPO_ID", unique=true, nullable=false)
    public long id;*/

    private long id;

    @JsonIgnore
    Snapshot snapshot;

    public String maintainername;

    public boolean current;

    public Maintainer(Snapshot snapshot, String maintainername) {
        super();
        this.snapshot = snapshot;
        this.maintainername  =maintainername;
    }

    public Maintainer() {
    }
}
