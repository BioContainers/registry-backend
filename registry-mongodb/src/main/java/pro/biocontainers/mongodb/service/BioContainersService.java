package pro.biocontainers.mongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.biocontainers.mongodb.model.BioContainer;
import pro.biocontainers.mongodb.repository.BioContainersRepository;

import java.util.Optional;

@Service
public class BioContainersService {

    @Autowired
    BioContainersRepository repository;

    /**
     * This method index a new Container using as import the container object.
     *
     * @param container {@link BioContainer}
     * @return Optional
     */
    public Optional<BioContainer> indexContainer(BioContainer container){
        return Optional.of(repository.save(container));
    }
}
