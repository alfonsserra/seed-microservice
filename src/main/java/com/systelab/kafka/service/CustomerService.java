package com.systelab.kafka.service;


import com.systelab.kafka.model.Action;
import com.systelab.kafka.model.Customer;
import com.systelab.kafka.model.CustomerEvent;
import com.systelab.kafka.repository.CustomerNotFoundException;
import com.systelab.kafka.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CustomerService {

    private CustomerRepository customerRepository;
    private KafkaTemplate<String, CustomerEvent> kafkaTemplate;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, KafkaTemplate<String, CustomerEvent> kafkaTemplate) {
        this.customerRepository = customerRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Page<Customer> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Optional<Customer> getCustomer(UUID id) {
        return customerRepository.findById(id);
    }

    public Customer addCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        kafkaTemplate.send("customer", new CustomerEvent(Action.CREATE, savedCustomer));
        return savedCustomer;
    }

    public Customer updateCustomer(UUID id, Customer customer) {
        return this.customerRepository.findById(id).map(existing -> {
            customer.setId(id);
            Customer savedCustomer = customerRepository.save(customer);
            kafkaTemplate.send("customer", new CustomerEvent(Action.UPDATE, savedCustomer));
            return savedCustomer;
        }).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public boolean removeCustomer(UUID id) {
        return this.customerRepository.findById(id).map(existing -> {
            customerRepository.delete(existing);
            kafkaTemplate.send("customer", new CustomerEvent(Action.DELETE, existing));
            return true;
        }).orElseThrow(() -> new CustomerNotFoundException(id));
    }
}