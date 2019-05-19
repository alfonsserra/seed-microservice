package com.systelab.seed.controller;

import com.systelab.seed.model.Center;
import com.systelab.seed.repository.CenterNotFoundException;
import com.systelab.seed.service.CenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(value = "Center management", description = "API for Center management", tags = {"Center"})
@RestController()
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")
@RequestMapping(value = "/seed/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class CenterController {

    private CenterService centerService;

    @Autowired
    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    @ApiOperation(value = "Get all Centers", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("centers")
    public Page<Center> getAllCenters(Pageable pageable) {
        return centerService.getCenters(pageable);
    }

    @ApiOperation(value = "Get Center", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("centers/{uid}")
    public Center getCenter(@PathVariable("id") String id) {
        return this.centerService.getCenter(id).orElseThrow(() -> new CenterNotFoundException(id));
    }

}