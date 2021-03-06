package pro.biocontainers.readers;

import java.util.List;
import java.util.Map;

public interface IContainerRecipe {

    String getSoftwareName();

    String getDescription();

    String getLicense();

    String getHomeURL();

    String getDocumentationURL();

    Map<String, List<String>> getExternalIds();

    String getSoftwareVersion();

}
