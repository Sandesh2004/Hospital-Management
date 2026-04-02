package com.hospital.management.controller;

import com.hospital.management.model.Appointment;
import com.hospital.management.model.Doctor;
import com.hospital.management.service.AppointmentService;
import com.hospital.management.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // ✅ FIXED
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    // 🌐 PUBLIC API
    @GetMapping
    public List<Doctor> getDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialization
    ) {
        return doctorService.search(name, specialization);
    }

    // 🔥 FIXED LOGIN MAPPING
    @GetMapping("/me")
    public Doctor getLoggedInDoctor(Authentication authentication) {

        String username = authentication.getName(); // ✅ FIXED

        return doctorService.getDoctorByUsername(username);
    }

    @GetMapping("/all")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // 📅 Appointments
    @GetMapping("/appointments")
    public List<Appointment> getAppointments(
            Authentication authentication,
            @RequestParam String date,
            @RequestParam(required = false) String name
    ) {
        String username = authentication.getName();

        Doctor doctor = doctorService.getDoctorByUsername(username);

        if (name != null && !name.isEmpty()) {
            return appointmentService.searchAppointments(doctor.getId(), date, name);
        }

        return appointmentService.getDoctorAppointments(doctor.getId(), date);
    }

    // ✅ Update status
    @PutMapping("/appointments/{id}/status")
    public Appointment updateStatus(
            @PathVariable String id,
            @RequestParam String status
    ) {
        return appointmentService.updateStatus(id, status);
    }

    // ✏️ Update doctor profile
    @PutMapping("/{id}")
    public Doctor updateDoctor(
            @PathVariable String id,
            @RequestBody Doctor doctorDetails
    ) {
        return doctorService.updateDoctor(id, doctorDetails);
    }

    // Get doctor by ID
    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable String id) {
        return doctorService.getDoctorById(id);
    }

    // 🗑️ Delete doctor
    @DeleteMapping("/{id}")
    public String deleteDoctor(@PathVariable String id) {
        doctorService.deleteDoctor(id);
        return "Doctor deleted successfully";
    }
}