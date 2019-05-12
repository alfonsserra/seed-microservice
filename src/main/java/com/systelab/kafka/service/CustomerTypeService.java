package com.systelab.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerTypeService {
    @KafkaListener(topics = "modulab", groupId = "group:high")
    public void listen(String message) {
        System.out.println("Received Messasge in group group:high: " + message);
    }
}
