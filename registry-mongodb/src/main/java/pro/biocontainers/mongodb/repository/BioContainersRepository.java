package pro.biocontainers.mongodb.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pro.biocontainers.mongodb.model.BioContainer;

import java.util.List;
import java.util.Optional;

@Repository
public interface BioContainersRepository extends MongoRepository<BioContainer, ObjectId> {

    @Override
    <S extends BioContainer> List<S> saveAll(Iterable<S> iterable);

    @Override
    <S extends BioContainer> S save(S s);

    @Override
    Optional<BioContainer> findById(ObjectId objectId);

    @Query("{'accession' : ?0}")
    Optional<BioContainer> findByAccession(String accession);

}
