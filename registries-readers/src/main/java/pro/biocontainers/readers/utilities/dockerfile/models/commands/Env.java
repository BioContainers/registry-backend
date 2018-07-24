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
public class Env extends Instruction {
    public long id;

    @JsonIgnore
    Snapshot snapshot;

    public String key;

    public String value;

    public String keyValue;

    public boolean current;

    public Env(Snapshot snapshot, String key, String value) {
        super();
        this.snapshot = snapshot;
        this.key = key;
        this.value = value;

        this.keyValue = key + ":" + value;

        if (this.keyValue.length() > 240) {
            this.keyValue = this.keyValue.substring(0, 240) + "...";
        }


    }

    public Env() {
    }
}
