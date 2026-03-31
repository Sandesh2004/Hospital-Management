package com.hospital.management.service;

import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Doctor;
import com.hospital.management.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // 🔍 Get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // 🔍 Search by name (homepage search)
    public List<Doctor> searchByName(String name) {
        List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCase(name);

        if (doctors.isEmpty()) {
            throw new ResourceNotFoundException("No doctors found with name: " + name);
        }

        return doctors;
    }

    // 🔍 Filter by specialization
    public List<Doctor> searchBySpecialization(String specialization) {
        List<Doctor> doctors = doctorRepository.findBySpecializationIgnoreCase(specialization);

        if (doctors.isEmpty()) {
            throw new ResourceNotFoundException("No doctors found for specialization: " + specialization);
        }

        return doctors;
    }

    // ➕ Add doctor (admin use)
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // 🔎 Get doctor by ID
    public Doctor getDoctorById(String id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }
}