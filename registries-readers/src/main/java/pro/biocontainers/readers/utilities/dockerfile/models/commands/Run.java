package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.Snapshot;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class Run extends Instruction{

    public long id;

    @JsonIgnore
    Snapshot snapshot;

    public int score;

    public String executable;

    public String allParams;

    public List<String> params;

    public boolean current;

    public Run(Snapshot snapshot,String executable, List<String> params) {
        this.snapshot = snapshot;
        this.executable=executable;

        List<String> paramsShortened = new ArrayList<>();
        for (String param: params){
            if (param.length() > 240){
                paramsShortened.add(param.substring(0, 240) + "...");
            }else{
                paramsShortened.add(param);

            }
        }
        this.params= paramsShortened;

        String allParams = "";
        for(String p: params){
            allParams += "¦"+ p;
        }

        if (allParams.length() > 240){
            this.allParams = allParams.substring(0, 240) + "...";
        }else{
            this.allParams = allParams;
        }

        if (allParams.length() > 240){
            this.allParams = allParams.substring(0, 240) + "...";
        }
    }

    public Run() {
    }
}
