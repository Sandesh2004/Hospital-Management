package com.hospital.management.repository;

import com.hospital.management.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient, String> {
    Optional<Patient> findByPhone(String phone);
    List<Patient> findByNameContainingIgnoreCase(String name);
}
