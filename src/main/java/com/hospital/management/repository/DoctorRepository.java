package com.hospital.management.repository;

import com.hospital.management.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    List<Doctor> findByNameContainingIgnoreCase(String name);

    List<Doctor> findBySpecializationIgnoreCase(String specialization);
}
