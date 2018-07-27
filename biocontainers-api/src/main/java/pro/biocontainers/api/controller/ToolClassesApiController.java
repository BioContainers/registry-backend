package pro.biocontainers.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pro.biocontainers.api.model.ToolClass;
import pro.biocontainers.api.service.ToolClassesApiService;

import java.util.List;

@Controller
@RequestMapping("${biocontainers.api.base-path}/toolClasses")
@Api(value = "toolClasses", description = "ToolClasses API", tags = {"ToolClasses",})
public class ToolClassesApiController {

    @Autowired
    private ToolClassesApiService service;

    /**
     * This endpoint returns all tool-classes available.
     *
     * @return A list of potential tool classes.
     */
    @RequestMapping(value = "", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    @ApiOperation(value = "List all tool types", nickname = "toolClassesGet", notes = "This endpoint returns all tool-classes available ", response = ToolClass.class, responseContainer = "List")
    public ResponseEntity<List<ToolClass>> get() {

        return new ResponseEntity<>(service.get(), HttpStatus.OK);
    }
}
