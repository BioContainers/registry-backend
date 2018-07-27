package pro.biocontainers.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pro.biocontainers.api.model.*;
import pro.biocontainers.api.service.ToolsApiService;

import java.util.List;

@Controller
@RequestMapping("${biocontainers.api.base-path}/tools")
@Api(value = "tools", description = "Tools API", tags = {"Tools",})
public class ToolsApiController {

    @Autowired
    private ToolsApiService service;

    /**
     * List all tools.
     *
     * @param id           A unique identifier of the tool, scoped to this registry, for example `123456`
     * @param registry     The image registry that contains the image.
     * @param organization The organization in the registry that published the image.
     * @param name         The name of the image.
     * @param toolname     The name of the tool.
     * @param description  The description of the tool.
     * @param author       The author of the tool.
     * @return An array of Tools that match the filter.
     */
    @RequestMapping(value = "", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    @ApiOperation(value = "List all tools", nickname = "toolsGet", notes = "This endpoint returns all tools available or a filtered subset using metadata query parameters. ", response = Tool.class, responseContainer = "List")
    public ResponseEntity<List<Tool>> get(@RequestParam(value = "id", required = false) String id,
                                          @RequestParam(value = "registry", required = false) String registry,
                                          @RequestParam(value = "organization", required = false) String organization,
                                          @RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "toolname", required = false) String toolname,
                                          @RequestParam(value = "description", required = false) String description,
                                          @RequestParam(value = "author", required = false) String author) {

        return new ResponseEntity<>(service.get(id, registry, organization, name, toolname, description, author),
                HttpStatus.OK);
    }

    /**
     * List one specific tool, acts as an anchor for self references.
     *
     * @param id A unique identifier of the tool, scoped to this registry, for example `123456`.
     * @return A tool.
     */
    @RequestMapping(value = "/{id}", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    @ApiOperation(value = "List one specific tool, acts as an anchor for self references", nickname = "toolsIdGet", notes = "This endpoint returns one specific tool (which has ToolVersions nested inside it)", response = Tool.class)
    public ResponseEntity<Tool> getById(@PathVariable("id") String id) {

        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    /**
     * Returns all versions of the specified tool.
     *
     * @param id A unique identifier of the tool, scoped to this registry.
     * @return An array of tool versions.
     */
    @RequestMapping(value = "/{id}/versions", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    @ApiOperation(value = "List versions of a tool", nickname = "toolsIdVersionsGet", notes = "Returns all versions of the specified tool", response = ToolVersion.class, responseContainer = "List")
    public ResponseEntity<List<ToolVersion>> getVersions(@PathVariable("id") String id) {

        return new ResponseEntity<>(service.getVersions(id), HttpStatus.OK);

    }

    /**
     * This endpoint returns one specific tool version.
     *
     * @param id        A unique identifier of the tool, scoped to this registry, for example `123456`.
     * @param versionId An identifier of the tool version for this particular tool registry for example `v1`.
     * @return A tool version.
     */
    @RequestMapping(value = "/{id}/versions/{version_id}", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    @ApiOperation(value = "List one specific tool version, acts as an anchor for self references", nickname = "toolsIdVersionsVersionIdGet", notes = "This endpoint returns one specific tool version", response = ToolVersion.class)
    public ResponseEntity<ToolVersion> getByVersionId(@PathVariable("id") String id, @PathVariable("version_id") String versionId) {

        return new ResponseEntity<>(service.getByVersionId(id, versionId), HttpStatus.OK);

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
    @RequestMapping(value = "/{id}/versions/{version_id}/containerfile", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    @ApiOperation(value = "Get the container specification(s) for the specified image.", nickname = "toolsIdVersionsVersionIdContainerfileGet", notes = "Returns the container specifications(s) for the specified image. For example, a CWL CommandlineTool can be associated with one specification for a container, a CWL Workflow can be associated with multiple specifications for containers", response = ToolContainerfile.class, responseContainer = "array")
    public ResponseEntity<List<ToolContainerfile>> getContainerfile(@PathVariable("id") String id,
                                                                    @PathVariable("version_id") String versionId) {

        return new ResponseEntity<>(service.getContainerfile(id, versionId), HttpStatus.OK);
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
    @RequestMapping(value = "/{id}/versions/{version_id}/{type}/descriptor", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    @ApiOperation(value = "Get the tool descriptor for the specified tool", nickname = "toolsIdVersionsVersionIdTypeDescriptorGet", notes = "Returns the descriptor for the specified tool (examples include CWL, WDL, or Nextflow documents).", response = ToolDescriptor.class)
    public ResponseEntity<ToolDescriptor> getDescriptor(@PathVariable("type") String type,
                                                        @PathVariable("id") String id,
                                                        @PathVariable("version_id") String versionId) {

        return new ResponseEntity<>(service.getDescriptor(type, id, versionId), HttpStatus.OK);
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
    @RequestMapping(value = "/{id}/versions/{version_id}/{type}/descriptor/{relative_path}", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    @ApiOperation(value = "Get additional tool descriptor files relative to the main file", nickname = "toolsIdVersionsVersionIdTypeDescriptorRelativePathGet", notes = "Descriptors can often include imports that refer to additional descriptors. This returns additional descriptors for the specified tool in the same or other directories that can be reached as a relative path. This endpoint can be useful for workflow engine implementations like cwltool to programmatically download all the descriptors for a tool and run it", response = ToolDescriptor.class)
    public ResponseEntity<ToolDescriptor> getDescriptorByRelativePath(@PathVariable("type") String type,
                                                                      @PathVariable("id") String id,
                                                                      @PathVariable("version_id") String versionId,
                                                                      @PathVariable("relative_path") String relativePath) {

        return new ResponseEntity<>(service.getDescriptorByRelativePath(type, id, versionId, relativePath), HttpStatus.OK);
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
    @RequestMapping(value = "/{id}/versions/{version_id}/{type}/files", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    @ApiOperation(value = "Get a list of objects that contain the relative path and file type", nickname = "toolsIdVersionsVersionIdTypeFilesGet", notes = "Get a list of objects that contain the relative path and file type. The descriptors are intended for use with the /tools/{id}/versions/{version_id}/{type}/descriptor/{relative_path} endpoint.", response = ToolFile.class, responseContainer = "array")
    public ResponseEntity<List<ToolFile>> getFiles(@PathVariable("type") String type,
                                                   @PathVariable("id") String id,
                                                   @PathVariable("version_id") String versionId) {

        return new ResponseEntity<>(service.getFiles(type, id, versionId), HttpStatus.OK);
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
    @RequestMapping(value = "/{id}/versions/{version_id}/{type}/tests", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    @ApiOperation(value = "Get a list of test JSONs", nickname = "toolsIdVersionsVersionIdTypeTestsGet", notes = "Get a list of test JSONs (these allow you to execute the tool successfully) suitable for use with this descriptor type.", response = ToolTests.class, responseContainer = "array")
    public ResponseEntity<List<ToolTests>> getTests(@PathVariable("type") String type,
                                                    @PathVariable("id") String id,
                                                    @PathVariable("version_id") String versionId) {

        return new ResponseEntity<>(service.getTests(type, id, versionId), HttpStatus.OK);
    }
}
