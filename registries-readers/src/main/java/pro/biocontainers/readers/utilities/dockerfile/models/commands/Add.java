package pro.biocontainers.readers.utilities.dockerfile.models.commands;

import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.Snapshot;

/**
 * Created by salizumberi-laptop on 01.11.2016.
 */

@Getter
@Setter
public class Add extends Instruction{
    public long id;


    Snapshot snapshot;
    public String source;

    public String sourceDestination;

    public String destination;

    public boolean current;

    public Add(Snapshot snapshot, String source, String destinatation) {
        super();
        this.snapshot = snapshot;
        this.source = source;
        this.destination=destinatation;

        this.sourceDestination = source +" -> " + destinatation;
        if (sourceDestination.length() > 240){
            this.sourceDestination = this.sourceDestination.substring(0, 240) + "...";
        }

    }

    public Add() {
    }
}
