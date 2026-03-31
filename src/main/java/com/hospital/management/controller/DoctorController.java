package com.hospital.management.controller;

import com.hospital.management.model.Doctor;
import com.hospital.management.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // 🌐 PUBLIC API (Homepage)
    @GetMapping
    public List<Doctor> getDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialization
    ) {
        if (name != null) {
            return doctorService.searchByName(name);
        }
        if (specialization != null) {
            return doctorService.searchBySpecialization(specialization);
        }
        return doctorService.getAllDoctors();
    }

    // 👨‍⚕️ DOCTOR DASHBOARD (secured)
    @GetMapping("/me")
    public String doctorDashboard() {
        return "Doctor dashboard working";
    }

    @GetMapping("/all")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }
}