package pro.biocontainers.mongodb.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pro.biocontainers.mongodb.model.BioContainerTool;

import java.util.List;
import java.util.Optional;

@Repository
public interface BioContainersRepository extends MongoRepository<BioContainerTool, String> {

    @Override
    <S extends BioContainerTool> List<S> saveAll(Iterable<S> iterable);

    @Override
    <S extends BioContainerTool> S save(S s);

    @Override
    Optional<BioContainerTool> findById(String objectId);

    @Query("{'accession' : ?0}")
    Optional<BioContainerTool> findByAccession(String accession);

}
