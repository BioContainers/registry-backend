package pro.biocontainers.mongodb.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pro.biocontainers.data.model.Tool;
import pro.biocontainers.data.model.ToolClass;
import pro.biocontainers.data.model.ToolVersion;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Builder
@Data
@Document(collection = "BioContainerTools")
public class BioContainerTool implements Tool {

    /** Native Identifier from MongoDB */
    @Id
    @Field("id")
    String id;

    @Indexed(name = "name")
    String name;

    /** Main URL where the user can download the container. **/
    @Indexed(name = "url")
    String url;

    @Field(value = "description")
    String description;

    @Indexed(name = "starred")
    Boolean starred;

    @Indexed(name = "version")
    String version;

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
    private List<BioContainerToolVersion> toolVersions;

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
        return this.version;
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

    @Override
    public Collection<? extends ToolVersion> getVersions() {
        return this.toolVersions;
    }
}
