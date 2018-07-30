package pro.biocontainers.api.service;

import org.dummycreator.ClassBindings;
import org.dummycreator.DummyCreator;
import org.springframework.stereotype.Service;
import pro.biocontainers.api.model.ToolClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ToolClassesApiService {

    private final DummyCreator dummyCreator = new DummyCreator(ClassBindings.defaultBindings());

    /**
     * This endpoint returns all tool-classes available.
     *
     * @return A list of potential tool classes.
     */
    public List<ToolClass> get() {
        return Arrays.asList(ToolClass.values());
    }
}
