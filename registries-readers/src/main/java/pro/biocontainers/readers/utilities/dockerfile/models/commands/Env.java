package pro.biocontainers.readers.utilities.dockerfile.models.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;


@Getter
@Setter
public class Env extends Instruction {
    public long id;

    @JsonIgnore
    DockerContainer dockerContainer;

    public String key;

    public String value;

    public String keyValue;

    public boolean current;

    public Env(DockerContainer dockerContainer, String key, String value) {
        super();
        this.dockerContainer = dockerContainer;
        this.key = key;
        this.value = value;

        this.keyValue = key + ":" + value;

        if (this.keyValue.length() > 240) {
            this.keyValue = this.keyValue.substring(0, 240) + "...";
        }


    }

    public Env() {
    }
}
