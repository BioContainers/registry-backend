package pro.biocontainers.readers.utilities.dockerfile.models.commands;

import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

@Getter
@Setter
public class Add extends Instruction{
    public long id;


    DockerContainer dockerContainer;
    public String source;

    public String sourceDestination;

    public String destination;

    public boolean current;

    public Add(DockerContainer dockerContainer, String source, String destinatation) {
        super();
        this.dockerContainer = dockerContainer;
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
