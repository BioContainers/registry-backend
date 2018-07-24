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
public class Copy extends Instruction{

    public long id;

    @JsonIgnore
    Snapshot snapshot;

    public String source;

    public String destination;

    public String sourceDestination;

    public boolean current;

    public Copy(Snapshot snapshot, String source, String destinatation) {
        super();
        this.source = source;
        this.snapshot = snapshot;
        this.destination=destinatation;

        this.sourceDestination = source +" -> " + destinatation;
        if (sourceDestination.length() > 240){
            this.sourceDestination = sourceDestination.substring(0, 240) + "...";
        }
    }

    public Copy() {
    }
}
