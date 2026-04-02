package com.hospital.management.repository;

import com.hospital.management.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    List<Doctor> findByNameContainingIgnoreCase(String name);

    List<Doctor> findBySpecializationIgnoreCase(String specialization);

    List<Doctor> findByNameContainingIgnoreCaseAndSpecializationIgnoreCase(String name, String specialization);

    Optional<Doctor> findByUserId(String userId);
}
