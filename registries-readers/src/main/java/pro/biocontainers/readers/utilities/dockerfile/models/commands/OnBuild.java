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
public class OnBuild extends Instruction{

    public long id;

    @JsonIgnore
    Snapshot snapshot;

    public boolean current;

    public String instruction;

    public String allParams;

    public OnBuild(Snapshot snapshot, String instruction, String allParams) {
        super();
        this.snapshot = snapshot;
        this.instruction =instruction;

        if (allParams.length() > 240){
            this.allParams = allParams.substring(0, 240) + "...";
        }else{
            this.allParams= allParams;
        }
    }

    public OnBuild() {

    }
}
