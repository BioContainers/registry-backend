package pro.biocontainers.api.service;

import org.dummycreator.ClassBindings;
import org.dummycreator.DummyCreator;
import org.springframework.stereotype.Service;
import pro.biocontainers.api.model.Metadata;

@Service
public class MetadataApiService {

    private final DummyCreator dummyCreator = new DummyCreator(ClassBindings.defaultBindings());

    /**
     * Return some metadata that is useful for describing this registry.
     *
     * @return Metadata object describing this service.
     */
    public Metadata get() {
        return dummyCreator.create(Metadata.class);

    }
}
