package pro.biocontainers.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import pro.biocontainers.api.model.Metadata;

@Service
public class MetadataApiService {

    private Metadata metadata;

    @Autowired
    public MetadataApiService(Metadata metadata){
        this.metadata = metadata;
    }

    /**
     * Return some metadata that is useful for describing this registry.
     *
     * @return Metadata object describing this service.
     */
    public Metadata get() {
        return metadata;
    }
}
