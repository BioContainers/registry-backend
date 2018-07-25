package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;


public class Comment extends Instruction{
    public long id;

    @JsonIgnore
    DockerContainer dockerContainer;

    public String instructionAfter;

    public String comment;

    public boolean current;

    public Comment(DockerContainer dockerContainer, String instructionAfter, String comment) {
        super();
        this.dockerContainer = dockerContainer;
        this.instructionAfter = instructionAfter;

        if (comment.length() > 240){
            this.comment = comment.substring(0, 240) + "...";
        }else{
            this.comment = comment;
        }
    }
    public Comment() {
    }
}
