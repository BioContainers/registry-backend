package pro.biocontainers.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pro.biocontainers.mongodb.model.BioContainerToolVersion;

public interface BioContainersToolVersionRepository extends MongoRepository<BioContainerToolVersion, String> {
}
