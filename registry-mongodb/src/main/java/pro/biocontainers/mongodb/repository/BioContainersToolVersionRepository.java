package pro.biocontainers.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pro.biocontainers.mongodb.model.BioContainerTool;
import pro.biocontainers.mongodb.model.BioContainerToolVersion;

import java.util.List;
import java.util.Optional;

public interface BioContainersToolVersionRepository extends MongoRepository<BioContainerToolVersion, String> {

    @Query("{'name' : ?0}")
    List<BioContainerToolVersion> findSoftwareName(String softwareName);
}
