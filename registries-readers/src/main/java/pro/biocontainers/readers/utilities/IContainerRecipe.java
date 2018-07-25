package pro.biocontainers.readers.utilities;

import java.util.List;
import java.util.Map;

public interface IContainerRecipe {

    public String getName();

    public String getDescription();

    public String getLicense();

    public String getHomeURL();

    public String getDocumentationURL();

    public Map<String, List<String>> getExternalIds();

}
