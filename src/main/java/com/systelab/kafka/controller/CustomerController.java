package com.systelab.kafka.controller;


import com.systelab.kafka.model.Customer;
import com.systelab.kafka.repository.CustomerNotFoundException;
import com.systelab.kafka.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
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
    public ResponseEntity<Page<Customer>> getAllCustomers(Pageable pageable) {
        return ResponseEntity.ok(customerService.getCustomers(pageable));
    }

    @ApiOperation(value = "Get Customer", notes = "")
    @GetMapping("customers/{uid}")
    @PermitAll
    public ResponseEntity<Customer> getCustomer(@PathVariable("uid") UUID id) {
        return this.customerService.getCustomer(id).map(ResponseEntity::ok).orElseThrow(() -> new CustomerNotFoundException(id));

    }
/*
    @ApiOperation(value = "Create a Customer", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("tenants/tenant")
    public ResponseEntity<Customer> createTenant(@RequestBody @ApiParam(value = "Customer", required = true) @Valid TenantRequestInfo p) {

        Customer customer = customerService.newTenant(p);
        if (customer !=null) {
            Customer createdCustomer = this.customerRepository.save(customer);
            URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(createdCustomer.getId()).toUri();
            return ResponseEntity.created(uri).body(createdCustomer);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


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