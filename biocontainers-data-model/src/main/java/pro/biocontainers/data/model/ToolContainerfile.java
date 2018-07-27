package pro.biocontainers.data.model;


/**
 * A containerfile is a document that describes how to build a particular container image.
 * Examples include Dockerfiles for creating Docker images and Singularity recipes for Singularity images
 *
 * @author ypriverol
 */


public interface ToolContainerfile {
    /**
     * Container File
     */
    String getContainerfile();

    String getURL();
}

