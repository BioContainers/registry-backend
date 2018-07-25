package pro.biocontainers.readers.utilities.dockerfile.models.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.Snapshot;


@Getter
@Setter
public class User extends Instruction{
    public long id;

    @JsonIgnore
    Snapshot snapshot;

    public String username;

    public boolean current;

    public User(Snapshot snapshot, String username) {
        super();
        this.snapshot = snapshot;
        this.username = username;
    }

    public User() {
    }
}