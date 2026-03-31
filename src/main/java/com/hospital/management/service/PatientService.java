package com.hospital.management.service;

import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Patient;
import com.hospital.management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // ➕ Create patient
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // 🔍 Get all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // 🔎 Get patient by ID
    public Patient getPatientById(String id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    // 🔍 Search patient by phone (important for receptionist)
    public Patient getPatientByPhone(String phone) {
        return patientRepository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with phone: " + phone));
    }
}