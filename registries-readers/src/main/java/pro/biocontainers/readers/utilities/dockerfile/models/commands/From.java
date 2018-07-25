package pro.biocontainers.readers.utilities.dockerfile.models.commands;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;

import java.io.Serializable;


@Getter
@Setter
public class From extends Instruction implements Serializable {


    private long id;

    @JsonIgnore
    DockerContainer dockerContainer;

    public String imagename ="" ;

    public boolean current;

    public double imageVersionNumber;

    public String imageVersionString  ="";

    public String digest  ="";

    public String fullName;

    public From(DockerContainer dockerContainer, String imagename) {
        this.dockerContainer = dockerContainer;
        this.imagename = imagename;
        this.fullName = imagename;
    }

    public From(DockerContainer dockerContainer, String imagename, double imageVersionNumber) {
        this.dockerContainer = dockerContainer;
        this.imagename = imagename;
        this.imageVersionNumber = imageVersionNumber;
        this.fullName = imagename + ":" + imageVersionNumber;

    }

    public From(DockerContainer dockerContainer, String imagename, String digest, String what) {
        this.dockerContainer = dockerContainer;
        this.imagename = imagename;

        if (what.equals("diggest")) {
            this.digest = digest;
            this.fullName = imagename + "@" +digest;

        } else {
            this.imageVersionString = digest;
            this.fullName = imagename + "@" +imageVersionString;
        }

    }

    public From() {
    }
}
