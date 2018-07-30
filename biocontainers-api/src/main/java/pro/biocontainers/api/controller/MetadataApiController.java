package pro.biocontainers.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pro.biocontainers.api.model.Metadata;
import pro.biocontainers.api.service.MetadataApiService;

@Controller
@Api(value = "metadata", description = "Metadata API", tags = {"Metadata"})
public class MetadataApiController {

    private MetadataApiService service;

    @Bean
    public MetadataApiService getService(Metadata metadata){
        this.service = new MetadataApiService(metadata);
        return this.service;
    }

    @RequestMapping(value = "${biocontainers.api.base-path}/metadata", produces = {"application/json", "text/plain"}, method = RequestMethod.GET)
    @ApiOperation(notes = "General Description of the API", value = "metadata", nickname = "metadata")
    public ResponseEntity<Metadata> get() {
        return new ResponseEntity<>(service.get(), HttpStatus.OK);
    }
}
