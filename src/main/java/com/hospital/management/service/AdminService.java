package com.hospital.management.service;

import com.hospital.management.dto.CreateUserRequest;
import com.hospital.management.exception.BadRequestException;
import com.hospital.management.exception.ResourceNotFoundException;
import com.hospital.management.model.Doctor;
import com.hospital.management.model.User;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
            doctor.setEmail(request.getEmail());
            doctor.setUserId(savedUser.getId());
            doctor.setPatientsPerHour(request.getPatientsPerHour());
            doctor.setAvailability(request.getAvailability());

            doctorRepository.save(doctor);
        }
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // Get user by ID
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // Get user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    // Delete user by ID
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // Update user role
    public User updateUserRole(String id, String newRole) {
        User user = getUserById(id);
        user.setRole(newRole);
        return userRepository.save(user);
    }
}