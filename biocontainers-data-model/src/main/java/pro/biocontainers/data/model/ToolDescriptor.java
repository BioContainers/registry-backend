package pro.biocontainers.data.model;



/**
 * A tool descriptor is a metadata document that describes one
 * or more tools.
 *
 * @author ypriverol
 */
public interface ToolDescriptor {

    DescriptorType getDescriptorType();

    String getDescriptor();

    String getUrl();
}

