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
public class StopSignal extends Instruction{
/*    @Id
    @Column(name="REPO_ID", unique=true, nullable=false)
    public long id;*/

    private long id;

    @JsonIgnore
    Snapshot snapshot;

    public String signal;

    public boolean current;

    public StopSignal(Snapshot snapshot, String signal) {
        super();
        this.snapshot = snapshot;
        this.signal = signal;
    }


    public StopSignal() {
    }
}
