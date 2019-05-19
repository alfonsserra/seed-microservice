package com.systelab.seed.controller;


import com.systelab.seed.model.Patient;
import com.systelab.seed.repository.PatientNotFoundException;
import com.systelab.seed.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Api(value = "Patient management", description = "API for Patient management", tags = {"Patient management"})
@RestController()
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")
@RequestMapping(value = "/seed/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @ApiOperation(value = "Get all Patients", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("patients")
    public Page<Patient> getAllPatients(Pageable pageable) {
        return patientService.getPatients(pageable);
    }

    @ApiOperation(value = "Get Patient", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("patients/{uid}")
    public Patient getPatient(@PathVariable("uid") UUID id) {
        return this.patientService.getPatient(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    @ApiOperation(value = "Create a Patient", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("patients/patient")
    @ResponseStatus(HttpStatus.CREATED)
    public Patient createPatient(@RequestBody @ApiParam(value = "Patient", required = true) @Valid Patient patient) {
        return patientService.addPatient(patient);
    }

    @ApiOperation(value = "Create or Update (idempotent) an existing Patient", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("patients/{uid}")
    public Patient updatePatient(@PathVariable("uid") UUID id, @RequestBody @ApiParam(value = "Patient", required = true) @Valid Patient patient) {
        return this.patientService.updatePatient(id, patient);
    }

    @ApiOperation(value = "Delete a Patient", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("patients/{uid}")
    public Boolean removePatient(@PathVariable("uid") UUID id) {
        return this.patientService.removePatient(id);
    }

}