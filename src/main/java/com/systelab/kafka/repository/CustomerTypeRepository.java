package com.systelab.kafka.repository;

import com.systelab.kafka.model.Customer;
import com.systelab.kafka.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, String> {

    Optional<CustomerType> findById(String id);

}