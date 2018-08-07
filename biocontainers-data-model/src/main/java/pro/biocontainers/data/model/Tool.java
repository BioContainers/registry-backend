package pro.biocontainers.data.model;


import java.util.Collection;

/**
 * A tool (or described tool) is defined as a tuple of a descriptor file (which potentially consists of multiple files),
 * a set of container images, and a set of instructions for creating those images.
 */

public interface Tool {

    /** Get URL in the repository **/
    String  getUrl();

    /** A unique identifier of the tool, scoped to this registry, for example `123456` or `123456_v1` **/
    String getId();

    /** The organization that published the image. **/
    String getOrganization();

    /** Get Tool Name */
    String getToolName();

    Collection<ToolClass> getToolClasses();

    String getDescription();

    /** Contact information for the author of this tool entry in the registry.
     * (More complex authorship information is handled by the descriptor)
     * **/
    String getAuthor();

    /** The version of this tool in the registry. Iterates when fields like the description, author, etc. are updated. **/
    String getMetaVersion();

    /** An array of IDs for the applications that are stored inside this tool (for example `https://bio.tools/tool/mytum.de/SNAP2/1`) **/
    Collection<String> getContains();

    /** Whether this tool has a checker tool associated with it */
    Boolean getHasChecker();

    /** Optional url to the checker tool that will
     *  exit successfully if this tool produced the expected result given test data. */
    String getCheckerUrl();

    /** Reports whether this tool has been verified by a specific organization or individual **/
    Boolean getVerified();

    /** Source of metadata that can support a verified tool, such as an email or URL **/
    String getVerifiedSource();

    /** Reports whether this tool has been signed. **/
    Boolean getSigned();

}

