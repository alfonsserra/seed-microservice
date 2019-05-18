package com.systelab.customer.service;


import com.systelab.customer.model.events.Action;
import com.systelab.customer.model.Customer;
import com.systelab.customer.model.events.CustomerEvent;
import com.systelab.customer.repository.CustomerNotFoundException;
import com.systelab.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

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
        sendToKafka(Action.CREATE,savedCustomer);
        return savedCustomer;
    }

    public Customer updateCustomer(UUID id, Customer customer) {
        return this.customerRepository.findById(id).map(existing -> {
            customer.setId(id);
            Customer savedCustomer = customerRepository.save(customer);
            sendToKafka(Action.UPDATE,savedCustomer);
            return savedCustomer;
        }).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public boolean removeCustomer(UUID id) {
        return this.customerRepository.findById(id).map(existing -> {
            customerRepository.delete(existing);
            sendToKafka(Action.DELETE,existing);
            return true;
        }).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    private void sendToKafka(Action action, Customer customer) {
        CustomerEvent data=new CustomerEvent(action, customer);
        ListenableFuture<SendResult<String, CustomerEvent>> future = kafkaTemplate.send("customer", data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, CustomerEvent>>() {
            @Override
            public void onSuccess(SendResult<String, CustomerEvent> result) {
                handleSuccess(data);
            }

            @Override
            public void onFailure(Throwable ex) {
                handleFailure(data, ex);
            }
        });
    }

    private void handleSuccess(CustomerEvent data) {
        log.info(data.toString());
    }

    private void handleFailure(CustomerEvent data, Throwable ex) {
        log.error(data.toString());
    }
}