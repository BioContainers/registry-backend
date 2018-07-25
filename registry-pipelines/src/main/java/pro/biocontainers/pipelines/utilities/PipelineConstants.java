package pro.biocontainers.pipelines.utilities;

public class PipelineConstants {
    public enum StepNames {

        READ_DOCKERHUB_REGISTRY_LIST("Read all Dockerhub Containers in the list"),
        READ_QUAYIO_REGISTRY_LIST("Read all QuayIO Containers in the list");

        String name;

        StepNames(String name) {
            this.name = name;
        }
    }


    public enum JobNames {
        READ_DOCKERHUB_CONTAINERS_JOB("Read all the containers from dockerhub");

        String name;

        JobNames(String name){
           this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
