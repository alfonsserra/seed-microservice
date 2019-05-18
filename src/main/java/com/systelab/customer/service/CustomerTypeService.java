package com.systelab.customer.service;

import com.systelab.customer.model.events.Action;
import com.systelab.customer.model.events.CustomerTypeEvent;
import com.systelab.customer.repository.CustomerTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerTypeService {

    private CustomerTypeRepository customerTypeRepository;

    @Autowired
    public CustomerTypeService(CustomerTypeRepository customerTypeRepository) {
        this.customerTypeRepository = customerTypeRepository;
    }

    @KafkaListener(topics = "customer-type", containerFactory = "customerTypeKafkaListenerContainerFactory")
    public void listen(CustomerTypeEvent event) {
        log.info("Received Customer Type Event: " + event);
        if (event.getAction() == Action.CREATE || event.getAction() == Action.UPDATE) {
            log.info("Save Customer Type " + event.getPayload().getName());
            this.customerTypeRepository.save(event.getPayload());
        } else if (event.getAction() == Action.DELETE) {
            log.info("Delete Customer Type " + event.getPayload().getName());
            this.customerTypeRepository.delete(event.getPayload());
        }
    }
}
