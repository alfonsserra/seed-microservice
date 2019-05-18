package com.systelab.kafka.service;


import com.systelab.kafka.model.Customer;
import com.systelab.kafka.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<Customer> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Optional<Customer> getCustomer(UUID id) {
        return customerRepository.findById(id);
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public boolean removeCustomer(Customer customer) {
        customerRepository.delete(customer);
        return true;
    }
}