package com.hospital.management.service;

import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Doctor;
import com.hospital.management.model.User;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> search(String name, String specialization) {

        if (name != null && specialization != null) {
            return doctorRepository
                    .findByNameContainingIgnoreCaseAndSpecializationIgnoreCase(name, specialization);
        }

        if (name != null) {
            return doctorRepository.findByNameContainingIgnoreCase(name);
        }

        if (specialization != null) {
            return doctorRepository.findBySpecializationIgnoreCase(specialization);
        }

        return doctorRepository.findAll();
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor getDoctorById(String id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
    }

    // 🔥 FIXED LOGIC
    public Doctor getDoctorByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return doctorRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
    }

    // Update doctor information
    public Doctor updateDoctor(String id, Doctor doctorDetails) {
        Doctor doctor = getDoctorById(id);
        
        if (doctorDetails.getName() != null) {
            doctor.setName(doctorDetails.getName());
        }
        if (doctorDetails.getSpecialization() != null) {
            doctor.setSpecialization(doctorDetails.getSpecialization());
        }
        if (doctorDetails.getEmail() != null) {
            doctor.setEmail(doctorDetails.getEmail());
        }
        if (doctorDetails.getPhone() != null) {
            doctor.setPhone(doctorDetails.getPhone());
        }
        if (doctorDetails.getExperience() > 0) {
            doctor.setExperience(doctorDetails.getExperience());
        }
        if (doctorDetails.getPatientsPerHour() > 0) {
            doctor.setPatientsPerHour(doctorDetails.getPatientsPerHour());
        }
        if (doctorDetails.getAvailability() != null) {
            doctor.setAvailability(doctorDetails.getAvailability());
        }

        return doctorRepository.save(doctor);
    }

    // Delete doctor
    public void deleteDoctor(String id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found");
        }
        doctorRepository.deleteById(id);
    }
}