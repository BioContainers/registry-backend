package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import pro.biocontainers.readers.utilities.dockerfile.Snapshot;

/**
 * Created by salizumberi-laptop on 01.11.2016.
 */


public class Comment extends Instruction{
    public long id;

    @JsonIgnore
    Snapshot snapshot;

    public String instructionAfter;

    public String comment;

    public boolean current;

    public Comment(Snapshot snapshot, String instructionAfter, String comment) {
        super();
        this.snapshot = snapshot;
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
