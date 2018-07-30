package pro.biocontainers.pipelines.utilities;

public class PipelineConstants {

    public static final String QUAYIO = "quay.io";
    public static final String DOCKERFILE = "Dockerfile";

    public enum StepNames {

        READ_DOCKERHUB_REGISTRY_LIST("Read all Dockerhub Containers in the list"),
        READ_QUAYIO_REGISTRY_LIST("Read all QuayIO Containers in the list"),
        ANNOTATE_QUAYIO_RECIPE("Annotate Containers with Conda Recipe"),
        ANNOTATE_DOCKERHUB_RECIPE("Annotate Containers with Dockerfile Recipe");

        String name;

        StepNames(String name) {
            this.name = name;
        }
    }


    public enum JobNames {
        READ_DOCKERHUB_CONTAINERS_JOB("Read all containers from dockerhub"),
        READ_QUAYIO_CONTAINERS_JOB("Read all containers from quay.io"),
        ANNOTATE_CONTAINERS_JOB("Annotate all the containers from Recipes");

        String name;

        JobNames(String name){
           this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
