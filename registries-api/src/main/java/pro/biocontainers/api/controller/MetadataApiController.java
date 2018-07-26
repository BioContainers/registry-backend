package pro.biocontainers.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pro.biocontainers.api.model.Metadata;
import pro.biocontainers.api.service.MetadataApiService;

@Controller
@RequestMapping("${registry.api.base-path}/metadata")
public class MetadataApiController {

    @Autowired
    private MetadataApiService service;

    /**
     * Return some metadata that is useful for describing this registry.
     *
     * @return Metadata object describing this service.
     */
    @RequestMapping(value = "", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    public ResponseEntity<Metadata> get() {
        return new ResponseEntity<>(service.get(), HttpStatus.OK);
    }
}
