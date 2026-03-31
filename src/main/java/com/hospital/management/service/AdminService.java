package com.hospital.management.service;

import com.hospital.management.dto.CreateUserRequest;
import com.hospital.management.exception.BadRequestException;
import com.hospital.management.model.Doctor;
import com.hospital.management.model.User;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(CreateUserRequest request) {

        // duplicate check
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists");
        }

        // create user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        // 🔥 if doctor → create doctor entity
        if ("DOCTOR".equalsIgnoreCase(request.getRole())) {

            // validation
            if (request.getSpecialization() == null) {
                throw new BadRequestException("Specialization is required for doctor");
            }

            Doctor doctor = new Doctor();
            doctor.setName(request.getName());
            doctor.setSpecialization(request.getSpecialization());
            doctor.setExperience(request.getExperience());
            doctor.setPhone(request.getPhone());
            doctor.setUserId(savedUser.getId());

            doctorRepository.save(doctor);
        }
    }
}