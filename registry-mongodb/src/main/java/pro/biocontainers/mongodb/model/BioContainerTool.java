package pro.biocontainers.mongodb.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pro.biocontainers.data.model.Tool;
import pro.biocontainers.data.model.ToolClass;
import pro.biocontainers.data.model.ToolVersion;

import java.util.Collection;
import java.util.List;

@Builder
@Data
@Document(collection = "BioContainerTool")
public class BioContainerTool implements Tool {

    /** Native Identifier from MongoDB */
    @Id
    @Field("uui")
    String uui;

    /** Id of the software **/
    @Field("id")
    String id;

    /** Nmae of the software can be duplicated **/
    @Indexed(name = "name")
    String name;

    @Field(value = "description")
    String description;

    /** Main URL where the user software can be found **/
    @Indexed(name = "urlHome")
    String urlHome;

    @Indexed(name = "starred")
    Boolean starred;

    @Indexed(name = "latestVersion")
    String latestVersion;

    @Indexed(name = "toolClasses")
    private List<ToolClass> toolClasses;

    @Field("organization")
    private String organization;

    @Field("author")
    private String author;

    @Field("otherTools")
    private List<String> otherTools;

    @Field("hasChecker")
    private Boolean hasChecker;

    @Field("checkerURL")
    private String checkerURL;

    @Field("isVerified")
    private Boolean isVerified;

    @Field("verifiedSource")
    private String verifiedSource;

    @Field("toolVersions")
    private List<String> toolVersions;

    @Field("externalIdentifiers")
    private List<String> externalIdentifiers;

    @Field("registryURL")
    private String registryURL;

    @Override
    public String getUrl() {
        return registryURL;
    }

    @Override
    public String getOrganization() {
        return this.organization;
    }

    @Override
    public String getToolName() {
        return this.name;
    }

    @Override
    public List<ToolClass> getToolClasses() {
        return this.toolClasses;
    }

    @Override
    public String getAuthor() {
        return this.author;
    }

    @Override
    public String getMetaVersion() {
        return this.latestVersion;
    }

    @Override
    public List<String> getContains() {
        return this.otherTools;
    }

    @Override
    public Boolean getHasChecker() {
        return this.hasChecker;
    }

    @Override
    public String getCheckerUrl() {
        return this.checkerURL;
    }

    @Override
    public Boolean getVerified() {
        return this.isVerified;
    }

    @Override
    public String getVerifiedSource() {
        return this.verifiedSource;
    }

    @Override
    public Boolean getSigned() {
        return false;
    }

}
