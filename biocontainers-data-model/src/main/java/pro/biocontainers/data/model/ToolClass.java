package pro.biocontainers.data.model;

/**
 * Describes a class (type) of tool allowing us to categorize
 *  - Workflow
 *  - Task
 *  - Service
 *  - Single Tool
 *  - Multiple Tool
 * tasks, and maybe even other entities (such as services) separately
 *
 * The BioContainers API has implemented a new classification system based on
 *
 * @author ypriverol
 */

public enum ToolClass {

    WORKFLOW("1", "Workflow", "A workflow Tool contains multiple tool, tasks"),
    TASK("2", "Task", "A task can be classify as independent process"),
    SINGLE_TOOL("3", "Tool", "A Single Tool is a Tool with only one binary or tool"),
    SERVICE("4", "Service", "A Service can be an API, Database, Web Service"),
    MULTIPLE_TOOL("5", "Multiple Tool", "Tool compose by multiple tool");

    private String id;

    private String name;

    private String description;

    ToolClass(String id, String name,
              String description) {
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

