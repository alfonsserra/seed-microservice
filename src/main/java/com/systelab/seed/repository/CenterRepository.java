package com.systelab.seed.repository;

import com.systelab.seed.model.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CenterRepository extends JpaRepository<Center, String> {

    Optional<Center> findById(String id);

}