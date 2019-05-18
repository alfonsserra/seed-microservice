package com.systelab.kafka.controller;


import com.systelab.kafka.model.Customer;
import com.systelab.kafka.repository.CustomerNotFoundException;
import com.systelab.kafka.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.UUID;

@Api(value = "Customer management", description = "API for Customer management", tags = {"Customer management"})
@RestController()
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")
@RequestMapping(value = "/example/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "Get all Customers", notes = "")
    @GetMapping("customers")
    @PermitAll
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerService.getCustomers(pageable);
    }

    @ApiOperation(value = "Get Customer", notes = "")
    @GetMapping("customers/{uid}")
    @PermitAll
    public Customer getCustomer(@PathVariable("uid") UUID id) {
        return this.customerService.getCustomer(id).orElseThrow(() -> new CustomerNotFoundException(id));

    }

    @ApiOperation(value = "Create a Customer", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("tenants/tenant")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @ApiParam(value = "Customer", required = true) @Valid Customer customer) {
        return customerService.addCustomer(customer);

    }

/*
    @ApiOperation(value = "Create or Update (idempotent) an existing Customer", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("tenants/{uid}")
    public ResponseEntity<Customer> updateTenant(@PathVariable("uid") UUID tenantId, @RequestBody @ApiParam(value = "Customer", required = true) @Valid Customer p) {
        return this.customerService
                .findById(tenantId)
                .map(existing -> {
                    p.setId(tenantId);
                    Customer customer = this.customerService.save(p);
                    URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
                    return ResponseEntity.created(selfLink).body(customer);
                }).orElseThrow(() -> new CustomerNotFoundException(tenantId));
    }


    @ApiOperation(value = "Delete a Customer", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("tenants/{uid}")
    public ResponseEntity<?> removeTenant(@PathVariable("uid") UUID tenantId) {
        return this.customerService.findById(tenantId)
                .map(tenant -> {
                    customerService.removeTenant(tenant);
                    customerRepository.delete(tenant);
                    return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new CustomerNotFoundException(tenantId));
    }
    */
}