package pro.biocontainers.api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pro.biocontainers.api.model.Metadata;
import pro.biocontainers.api.service.MetadataApiService;

@Controller
public class MetadataApiController {
    @Autowired
    private MetadataApiService service;

    @ApiOperation(notes = "General Description of the API", value = "metadata", nickname = "metadata", tags = {"metadata"} )
    @RequestMapping(value = "${registry.api.base-path}/metadata", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    public ResponseEntity<Metadata> get() {
        return new ResponseEntity<>(service.get(), HttpStatus.OK);
    }
}
