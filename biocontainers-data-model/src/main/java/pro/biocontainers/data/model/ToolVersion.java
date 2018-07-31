package pro.biocontainers.data.model;


import java.util.Collection;

/**
 * A tool version describes a particular iteration of a tool as described by a reference to a specific
 * image and/or documents.
 *
 * @author ypriverol
 */

public interface ToolVersion {

    /** The name of the version. **/
    String getName();

    /** Get description of the Version **/

    /** The URL for this tool in this registry **/
    String getUrl();

    /** An identifier of the version of this tool
     * for this particular tool registry **/
    String getId();

    /** The docker path to the image (and version) for this tool **/
    String getLatestImage();

    /** A URL to a Singularity registry is provided when a specific type of
     image does not use ids in the Docker format. Used along with
     image_name to locate a specific image. **/
    String getRegistryUrl();

    /** Used in conjunction with a registry_url if provided to locate images **/
    Collection<? extends ContainerImage> getImages();

    /** Descriptor Type **/
    Collection<? extends DescriptorType> getDescriptorTypes();

    /** Has a Build Recipe (DockerFile or Conda Recipe) **/
    Boolean getContainerFile();

    /**  The version of this tool version in the registry. Iterates when
     fields like the description, author, etc. are updated. **/
    String getMetaVersion();

    /** Verified **/
    Boolean isVerified();

    /** Source of Verification **/
    String getVerifiedSource();

    /** Get the name in a hash useful for searching strategies*/
    String getHashName();

}

