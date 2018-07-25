package pro.biocontainers.readers.utilities.dockerfile.models.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.Snapshot;



@Getter
@Setter
public class WorkDir extends Instruction{
   public long id;

    @JsonIgnore
    Snapshot snapshot;

    public String path;

    public boolean current;

    public WorkDir(Snapshot snapshot, String path) {
        super();
        this.snapshot = snapshot;
        this.path= path;
    }

    public WorkDir() {
    }
}
