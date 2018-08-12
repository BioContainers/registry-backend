package pro.biocontainers.pipelines.utilities;

public class PipelineConstants {

    public static final String QUAYIO = "quay.io";
    public static final String DOCKERFILE = "Dockerfile";
    public static final String META_YAML  = "meta.yaml";
    public static final String META_YML   = "meta.yml";

    public enum StepNames {

        READ_DOCKERHUB_REGISTRY_LIST("listDockerHubRegistry", "Read all Dockerhub Containers in the list"),
        READ_QUAYIO_REGISTRY_LIST("readQUAIORegistry", "Read all QuayIO Containers in the list"),
        ANNOTATE_QUAYIO_RECIPE("annotateQUAIORecipe", "Annotate Containers with Conda Recipe"),
        ANNOTATE_BIOTOOLS_RECIPE("annotateBioToolsMetadata", "Annotate BioTools Metadata"),
        ANNOTATE_DOCKERHUB_RECIPE("annotateDockerHubRecipe", "Annotate Containers with Dockerfile Recipe");

        String name;
        String description;

        StepNames(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }


    public enum JobNames {
        READ_DOCKERHUB_CONTAINERS_JOB("importDockerHubToMongoDB", "Read all containers from dockerhub"),
        READ_QUAYIO_CONTAINERS_JOB("importQuayIOToMongoDB", "Read all containers from quay.io"),
        ANNOTATE_CONTAINERS_JOB("annotateToolFromContainers", "Annotate all the containers from Recipes");

        String name;
        String description;

        JobNames(String name, String description){
           this.name = name;
           this.description = description;
        }

        public String getName() {
            return name;
        }
    }
}
