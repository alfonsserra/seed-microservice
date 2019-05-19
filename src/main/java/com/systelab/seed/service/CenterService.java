package com.systelab.seed.service;

import com.systelab.seed.model.Center;
import com.systelab.seed.model.events.Action;
import com.systelab.seed.model.events.CenterEvent;
import com.systelab.seed.repository.CenterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CenterService {

    private CenterRepository centerRepository;

    @Autowired
    public CenterService(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    @KafkaListener(topics = "center", containerFactory = "centerKafkaListenerContainerFactory")
    public void listen(CenterEvent event) {
        log.info("Received Center Event: " + event);
        if (event.getAction() == Action.CREATE || event.getAction() == Action.UPDATE) {
            log.info("Save Center " + event.getPayload().getName());
            this.centerRepository.save(event.getPayload());
        } else if (event.getAction() == Action.DELETE) {
            log.info("Delete Center " + event.getPayload().getName());
            this.centerRepository.delete(event.getPayload());
        }
    }

    public Page<Center> getCenters(Pageable pageable) {
        return centerRepository.findAll(pageable);
    }

    public Optional<Center> getCenter(String id) {
        return centerRepository.findById(id);
    }

}
