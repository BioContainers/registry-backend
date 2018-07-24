package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.Snapshot;

import java.util.List;

/**
 * Created by salizumberi-laptop on 01.11.2016.
 */

@Getter
@Setter
public class Cmd extends Instruction{
/*    @Id
    @Column(name="REPO_ID", unique=true, nullable=false)
    public long id;*/

    private long id;

    @JsonIgnore
    public Snapshot snapshot;

    public String executable;

    public List<String> params;

    public boolean current;

    public String allParams;

    public Cmd(Snapshot snapshot, String executable, List<String> params) {
        this.snapshot = snapshot;
        this.executable=executable;
        this.params=params;

        String allParams = "";
        for(String p: params){
            allParams += "¦"+ p;
        }

        if (allParams.length() > 240){
            this.allParams = allParams.substring(0, 240) + "...";
        }else{
            this.allParams = allParams;
        }
    }

    public Cmd() {
    }
}
