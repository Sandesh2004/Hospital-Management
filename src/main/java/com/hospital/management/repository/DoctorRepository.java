package com.hospital.management.repository;

import com.hospital.management.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    List<Doctor> findByNameContainingIgnoreCase(String name);

    List<Doctor> findBySpecializationIgnoreCase(String specialization);

    List<Doctor> findByNameContainingIgnoreCaseAndSpecializationIgnoreCase(String name, String specialization);

    Page<Doctor> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Doctor> findBySpecializationIgnoreCase(String specialization, Pageable pageable);

    Page<Doctor> findByNameContainingIgnoreCaseAndSpecializationIgnoreCase(String name, String specialization, Pageable pageable);

    Optional<Doctor> findByUserId(String userId);
}
