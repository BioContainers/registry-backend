package pro.biocontainers.api.service;

import org.dummycreator.ClassBindings;
import org.dummycreator.DummyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.biocontainers.api.model.*;
import pro.biocontainers.data.model.ContainerType;
import pro.biocontainers.mongodb.model.BioContainerTool;
import pro.biocontainers.mongodb.model.BioContainerToolVersion;
import pro.biocontainers.mongodb.model.ContainerFile;
import pro.biocontainers.mongodb.model.ContainerImage;
import pro.biocontainers.mongodb.service.BioContainersService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToolsApiService {

//    BioContainersService service;

    BioContainersService service;

    @Value("${biocontainers-registry}")
    private String apiToolURL;


    public ToolsApiService(BioContainersService service) {
        this.service = service;
    }

    private final DummyCreator dummyCreator = new DummyCreator(ClassBindings.defaultBindings());

    /**
     * List all tools.
     *
     * @param id           A unique identifier of the tool, scoped to this registry, for example `123456`
     * @param name         The name of the image.
     * @param toolname     The name of the tool.
     * @param description  The description of the tool.
     * @param author       The author of the tool.
     * @return An array of Tools that match the filter.
     */
    public List<Tool> get(String id,  String name,
                          String toolname, String description, String author) {

        List<Tool> tools;
        List<BioContainerTool> mongoTools;
        if((id == null && name==null && toolname==null && description==null && author == null)){
            mongoTools = service.findAll();

        }else{
            mongoTools = service.filterAll(id, name, toolname, description, author);
        }

        // COnvert the BioContainersTool to a  Tools representation.
        tools = mongoTools.stream().map( x-> {

            String authorTool = (x.getAuthor() != null && !x.getAuthor().isEmpty())?x.getAuthor().stream().findAny().get():null;

            String url = apiToolURL.replace("%%tool_accession%%", x.getId());

            return Tool.builder()
                    .id(x.getId())
                    .author(authorTool)
                    .toolname(x.getName())
                    .description(x.getDescription())
                    .verified(true)
                    .contains(x.getContains())
                    .url(url)
                    .toolclass(x.getToolClasses().stream().map(y-> ToolClass.getByName(y.getName())).collect(Collectors.toList()))
                    .build();
        }).collect(Collectors.toList());

        return tools;
    }

    /**
     * List one specific tool, acts as an anchor for self references.
     *
     * @param id A unique identifier of the tool, scoped to this registry, for example `123456`.
     * @return A tool.
     */

    public Tool getById(String id) {

        Tool tool = null;
        Optional<BioContainerTool> toolOptional = service.findToolByAccession(id);
        if(toolOptional.isPresent()){
            BioContainerTool x = toolOptional.get();
            String authorTool = (x.getAuthor() != null && !x.getAuthor().isEmpty())?x.getAuthor().stream().findAny().get():null;

            String url = apiToolURL.replace("%%tool_accession%%", x.getId());

            return Tool.builder()
                    .id(x.getId())
                    .author(authorTool)
                    .toolname(x.getName())
                    .description(x.getDescription())
                    .verified(true)
                    .contains(x.getContains())
                    .url(url)
                    .toolclass(x.getToolClasses().stream().map(y-> ToolClass.getByName(y.getName())).collect(Collectors.toList()))
                    .build();
        }

        return tool;
    }

    /**
     * Returns all versions of the specified tool.
     *
     * @param id A unique identifier of the tool, scoped to this registry.
     * @return An array of tool versions.
     */

    public List<ToolVersion> getVersions(String id) {

        List<BioContainerToolVersion> mongoVersions = service.findVersions(id);
        List<ToolVersion> versions = new ArrayList<>();
        if(mongoVersions != null && mongoVersions.size() > 0){
            versions = mongoVersions.stream().map(x -> {
                List<? extends ContainerImage> dockerImages = (x.getImages())
                        .stream()
                        .sorted(Comparator.comparing(ContainerImage::getLastUpdate))
                        .collect(Collectors.toList());

                ContainerImage latestImage = dockerImages.stream().findFirst().get();

                return ToolVersion
                        .builder()
                        .metaVersion(x.getMetaVersion())
                        .name(x.getName())
                        .id(x.getMetaVersion())
                        .containerfile(latestImage.getContainerType() == ContainerType.DOCKER)
                        .verified(true)
                        .latestImage(latestImage.getId())
                        .imageName(x.getName() + "/" + x.getMetaVersion() + "/" + latestImage.getTag())
                        .images(dockerImages.stream().map(ContainerImage::getTag).collect(Collectors.toList()))
                        .containerImages(dockerImages)
                        .build();

            }).collect(Collectors.toList());
        }
        return versions;
    }

    /**
     * This endpoint returns one specific tool version.
     *
     * @param id        A unique identifier of the tool, scoped to this registry, for example `123456`.
     * @param versionId An identifier of the tool version for this particular tool registry for example `v1`.
     * @return A tool version.
     */
    public ToolVersion getByVersionId(String id, String versionId) {
        BioContainerToolVersion container = service.findVersions(id)
                .stream()
                .filter(x -> x.getMetaVersion().equalsIgnoreCase(versionId)).findAny().get();

        List<? extends ContainerImage> dockerImages = (container.getImages())
                .stream()
                .sorted(Comparator.comparing(ContainerImage::getLastUpdate))
                .collect(Collectors.toList());
        ContainerImage latestImage = dockerImages.stream().findFirst().get();

        return ToolVersion.builder()
                .metaVersion(container.getMetaVersion())
                .name(container.getName())
                .id(container.getMetaVersion())
                .containerfile(latestImage.getContainerType() == ContainerType.DOCKER)
                .verified(true)
                .latestImage(latestImage.getId())
                .imageName(container.getName() + "/" + container.getMetaVersion() + "/" + latestImage.getTag())
                .images(dockerImages.stream().map(ContainerImage::getTag).collect(Collectors.toList()))
                .containerImages(dockerImages)
                .build();
    }

    /**
     * Returns the container specifications(s) for the specified image.
     * For example, a CWL CommandlineTool can be associated with one specification
     * for a container, a CWL Workflow can be associated with multiple specifications for containers.
     *
     * @param id        A unique identifier of the tool, scoped to this registry, for example `123456`.
     * @param versionId An identifier of the tool version for this particular tool registry for example `v1`.
     * @return An array of the tool containerfile.
     */
    public List<ToolContainerfile> getContainerfile(String id, String versionId) {
        List<BioContainerToolVersion> mongoVersions = service.findVersions(id);
        List<BioContainerToolVersion> containers = mongoVersions.stream().filter(x -> x.getMetaVersion().equalsIgnoreCase(versionId)).collect(Collectors.toList());
        List<ToolContainerfile> images = new ArrayList<>();
        containers.stream().forEach( x-> {
            x.getImages().forEach( y-> {
                ContainerFile file = (ContainerFile) y.getContainerFile();
                images.add(ToolContainerfile
                        .builder()
                        .containerfile(file.getContainerfile())
                        .url(file.getURL())
                .build());
            });
            });
        return images;
        }

    /**
     * Returns the descriptor for the specified tool (examples include CWL, WDL, or Nextflow documents).
     *
     * @param type      The output type of the descriptor. If not specified, it is up to the
     *                  underlying implementation to determine which output type to return.
     *                  Plain types return the bare descriptor while the "non-plain" types
     *                  return a descriptor wrapped with metadata. Allowable values include
     *                  "CWL", "WDL", "NFL", "PLAIN_CWL", "PLAIN_WDL", "PLAIN_NFL".
     * @param id        A unique identifier of the tool, scoped to this registry, for example `123456`.
     * @param versionId An identifier of the tool version for this particular tool registry for example `v1`.
     * @return The tool descriptor.
     */
    public ToolDescriptor getDescriptor(String type, String id, String versionId) {

        return dummyCreator.create(ToolDescriptor.class);

    }


    /**
     * Descriptors can often include imports that refer to additional
     * descriptors. This returns additional descriptors for the specified tool
     * in the same or other directories that can be reached as a relative path.
     * This endpoint can be useful for workflow engine implementations like
     * cwltool to programmatically download all the descriptors for a tool and run it.
     *
     * @param type         The output type of the descriptor. If not specified, it is up to the
     *                     underlying implementation to determine which output type to return.
     *                     Plain types return the bare descriptor while the "non-plain" types
     *                     return a descriptor wrapped with metadata. Allowable values include
     *                     "CWL", "WDL", "NFL", "PLAIN_CWL", "PLAIN_WDL", "PLAIN_NFL".
     * @param id           A unique identifier of the tool, scoped to this registry, for example `123456`.
     * @param versionId    An identifier of the tool version for this particular tool registry for example `v1`.
     * @param relativePath A relative path to the additional file (same directory or
     *                     subdirectories), for example 'foo.cwl' would return a 'foo.cwl' from
     *                     the same directory as the main descriptor. 'nestedDirectory/foo.cwl'
     *                     would return the file  from a nested subdirectory.  Unencoded paths
     *                     such 'sampleDirectory/foo.cwl' should also be allowed.
     * @return The tool descriptor.
     */
    public ToolDescriptor getDescriptorByRelativePath(String type, String id,
                                                      String versionId, String relativePath) {

        return dummyCreator.create(ToolDescriptor.class);

    }

    /**
     * Get a list of objects that contain the relative path and file type.
     *
     * @param type      The output type of the descriptor. Examples of allowable values are
     *                  "CWL", "WDL", and "NextFlow.".
     * @param id        A unique identifier of the tool, scoped to this registry, for example `123456`.
     * @param versionId An identifier of the tool version for this particular tool registry for example `v1`.
     * @return The array of File JSON responses.
     */
    public List<ToolFile> getFiles(String type, String id, String versionId) {

        ArrayList<ToolFile> toolfiles = new ArrayList<>();
        toolfiles.add(dummyCreator.create(ToolFile.class));
        toolfiles.add(dummyCreator.create(ToolFile.class));
        toolfiles.add(dummyCreator.create(ToolFile.class));
        return toolfiles;

    }


    /**
     * Get a list of test JSONs (these allow you to execute the tool successfully)
     * suitable for use with this descriptor type.
     *
     * @param type      The type of the underlying descriptor. Allowable values include
     *                  "CWL", "WDL", "NFL", "PLAIN_CWL", "PLAIN_WDL", "PLAIN_NFL". For
     *                  example, "CWL" would return an list of ToolTests objects while
     *                  "PLAIN_CWL" would return a bare JSON list with the content of the tests.
     * @param id        A unique identifier of the tool, scoped to this registry, for example `123456`.
     * @param versionId An identifier of the tool version for this particular tool registry for example `v1`.
     * @return The tool test JSON response.
     */
    public List<ToolTests> getTests(String type, String id, String versionId) {

        ArrayList<ToolTests> toolTests = new ArrayList<>();
        toolTests.add(dummyCreator.create(ToolTests.class));
        toolTests.add(dummyCreator.create(ToolTests.class));
        toolTests.add(dummyCreator.create(ToolTests.class));
        return toolTests;

    }

    /**
     * Return the ContainerImages for an specific software version
     * @param id Software ID
     * @param versionId Tool version ID
     * @return Container Images
     */
    public List<pro.biocontainers.api.model.ContainerImage> getContainerImages(String id, String versionId) {
        List<BioContainerToolVersion> mongoVersions = service.findVersions(id);
        Optional<BioContainerToolVersion> container = mongoVersions.stream().filter(x -> x.getMetaVersion().equalsIgnoreCase(versionId)).findFirst();
        List<pro.biocontainers.api.model.ContainerImage> images = new ArrayList<>();
        if (container.isPresent()) {
            images = container.get().getImages().stream().map(x -> pro.biocontainers.api.model.ContainerImage
                    .builder()
                    .accession(((ContainerImage) x).getId())
                    .tag(((ContainerImage) x).getTag())
                    .downloads(((ContainerImage) x).getDownloads())
                    .lastUpdate(((ContainerImage) x).getLastUpdate())
                    .build()).collect(Collectors.toList());

        }
        return images;
    }
}
