package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.Snapshot;

/**
 * Created by salizumberi-laptop on 01.11.2016.
 */

@Setter
@Getter
public class Volume extends Instruction{
    public long id;


    @JsonIgnore
    Snapshot snapshot;

    public String value;

    public boolean current;

    public Volume(Snapshot snapshot, String match) {
        super();
        this.snapshot = snapshot;
        this.value = match;
    }

    public Volume() {
    }
}
