package pro.biocontainers.readers.utilities.dockerfile.models.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.Snapshot;

@Getter
@Setter
public class Arg extends Instruction{
    public long id;


    @JsonIgnore
    Snapshot snapshot;
    public String arg;
    public boolean current;

    public Arg(Snapshot snapshot, String arg) {
        super();
        this.snapshot =snapshot;
        this.arg = arg;
    }

    public Arg() {
    }
}
