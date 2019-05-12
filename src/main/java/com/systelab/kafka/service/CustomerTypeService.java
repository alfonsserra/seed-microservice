package com.systelab.kafka.service;

import com.systelab.kafka.model.CustomerTypeAction;
import com.systelab.kafka.repository.CustomerTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerTypeService {

    private Logger logger = LoggerFactory.getLogger(CustomerTypeService.class);
    private CustomerTypeRepository customerTypeRepository;

    @Autowired
    public CustomerTypeService(CustomerTypeRepository customerTypeRepository) {
        this.customerTypeRepository = customerTypeRepository;
    }

    @KafkaListener(topics = "modulab", containerFactory = "customerTypeKafkaListenerContainerFactory")
    public void listen(CustomerTypeAction action) {
        System.out.println("Received Message in group group:high: " + action);
        this.customerTypeRepository.save(action.getType());
    }
}
