package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class Run extends Instruction{

    public long id;

    @JsonIgnore
    DockerContainer dockerContainer;

    public int score;

    public String executable;

    public String allParams;

    public List<String> params;

    public boolean current;

    public Run(DockerContainer dockerContainer, String executable, List<String> params) {
        this.dockerContainer = dockerContainer;
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

        StringBuilder allParams = new StringBuilder();
        for(String p: params){
            allParams.append("¦").append(p);
        }

        if (allParams.length() > 240){
            this.allParams = allParams.substring(0, 240) + "...";
        }else{
            this.allParams = allParams.toString();
        }

        if (allParams.length() > 240){
            this.allParams = allParams.substring(0, 240) + "...";
        }
    }

    public Run() {
    }
}
