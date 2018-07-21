package pro.biocontainers.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pro.biocontainers.model.BioContainer;

public interface BioContainersRepository extends MongoRepository<BioContainer, ObjectId> {

}
