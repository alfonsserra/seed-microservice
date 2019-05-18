package com.systelab.customer.repository;

import com.systelab.customer.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, String> {

    Optional<CustomerType> findById(String id);

}