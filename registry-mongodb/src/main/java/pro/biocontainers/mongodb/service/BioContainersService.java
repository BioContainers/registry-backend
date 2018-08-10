package pro.biocontainers.mongodb.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import pro.biocontainers.mongodb.model.BioContainerTool;
import pro.biocontainers.mongodb.model.BioContainerToolVersion;
import pro.biocontainers.mongodb.repository.BioContainersRepository;
import pro.biocontainers.mongodb.repository.BioContainersToolVersionRepository;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class BioContainersService {

    @Autowired
    BioContainersRepository repository;

    @Autowired
    BioContainersToolVersionRepository versionRepository;

    /**
     * This method index a new Container using as import the container object.
     *
     * @param container {@link BioContainerTool}
     * @return Optional
     */
    public Optional<BioContainerTool> indexContainer(BioContainerTool container){
        try {
            container = repository.save(container);
        }catch(DuplicateKeyException ex){
           log.error("A BioContainerTool with similar accession already exists!!!");
        }
        return Optional.of(container);
    }

    /**
     * Find all Containers
     * @return
     */
    public List<BioContainerTool> findAll(){
        return repository.findAll();
    }

    public void updateContainer(BioContainerTool bioContainerTool) {
        try {
            if(bioContainerTool.getId() != null){
                repository.save(bioContainerTool);
                log.info("The following container has been updated -- " + bioContainerTool.getId());
            }
        }catch(DuplicateKeyException ex){
            log.error("A BioContainerTool with similar accession already exists!!!");
        }
    }

    public Optional<BioContainerToolVersion> indexToolVersion(BioContainerToolVersion mongoToolVersion) {
        try {
            mongoToolVersion = versionRepository.save(mongoToolVersion);
        }catch(DuplicateKeyException ex){
            log.error("A BioContainerTool Version with similar accession already exists!!!");
        }
        return Optional.of(mongoToolVersion);
    }

    public List<BioContainerToolVersion> findVersions(String id) {
        return versionRepository.findSoftwareName(id);
    }

    public List<BioContainerToolVersion> findAllToolVersion(){
        return versionRepository.findAll();
    }

    public Optional<BioContainerTool> findToolByAccession(String id) {
        return repository.findByAccession(id);
    }
}
