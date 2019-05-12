package com.systelab.kafka.service;

import com.systelab.kafka.model.CustomerTypeEvent;
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
    public void listen(CustomerTypeEvent event) {
        logger.info("Received Customer Type Event: " + event);
        this.customerTypeRepository.save(event.getType());
    }
}
