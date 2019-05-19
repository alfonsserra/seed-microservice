package com.systelab.seed.service;


import com.systelab.seed.model.Patient;
import com.systelab.seed.model.events.Action;
import com.systelab.seed.model.events.PatientEvent;
import com.systelab.seed.repository.PatientNotFoundException;
import com.systelab.seed.repository.PatientRepository;
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
public class PatientService {

    private PatientRepository patientRepository;
    private KafkaTemplate<String, PatientEvent> kafkaTemplate;

    @Autowired
    public PatientService(PatientRepository patientRepository, KafkaTemplate<String, PatientEvent> kafkaTemplate) {
        this.patientRepository = patientRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Page<Patient> getPatients(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    public Optional<Patient> getPatient(UUID id) {
        return patientRepository.findById(id);
    }

    public Patient addPatient(Patient patient) {
        Patient savedPatient = patientRepository.save(patient);
        sendToKafka(Action.CREATE, savedPatient);
        return savedPatient;
    }

    public Patient updatePatient(UUID id, Patient patient) {
        return this.patientRepository.findById(id).map(existing -> {
            patient.setId(id);
            Patient savedPatient = patientRepository.save(patient);
            sendToKafka(Action.UPDATE, savedPatient);
            return savedPatient;
        }).orElseThrow(() -> new PatientNotFoundException(id));
    }

    public boolean removePatient(UUID id) {
        return this.patientRepository.findById(id).map(existing -> {
            patientRepository.delete(existing);
            sendToKafka(Action.DELETE,existing);
            return true;
        }).orElseThrow(() -> new PatientNotFoundException(id));
    }

    private void sendToKafka(Action action, Patient patient) {
        PatientEvent data=new PatientEvent(action, patient);
        ListenableFuture<SendResult<String, PatientEvent>> future = kafkaTemplate.send("patient", data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, PatientEvent>>() {
            @Override
            public void onSuccess(SendResult<String, PatientEvent> result) {
                handleSuccess(data);
            }

            @Override
            public void onFailure(Throwable ex) {
                handleFailure(data, ex);
            }
        });
    }

    private void handleSuccess(PatientEvent data) {
        log.info(data.toString());
    }

    private void handleFailure(PatientEvent data, Throwable ex) {
        log.error(data.toString());
    }
}