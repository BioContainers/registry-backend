package pro.biocontainers.api.service;

import org.dummycreator.ClassBindings;
import org.dummycreator.DummyCreator;
import org.springframework.stereotype.Service;
import pro.biocontainers.api.model.ToolClass;

import java.util.ArrayList;
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

        ArrayList<ToolClass> toolClasses = new ArrayList<>();
        toolClasses.add(dummyCreator.create(ToolClass.class));
        toolClasses.add(dummyCreator.create(ToolClass.class));
        toolClasses.add(dummyCreator.create(ToolClass.class));
        return toolClasses;
    }
}
