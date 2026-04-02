package com.hospital.management.controller;

import com.hospital.management.model.Appointment;
import com.hospital.management.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public Appointment book(@RequestBody Appointment appointment) {
        return appointmentService.bookAppointment(appointment);
    }

    @GetMapping("/remaining")
    public int getRemainingSlots(
            @RequestParam String doctorId,
            @RequestParam String date
    ) {
        return appointmentService.getRemainingSlots(doctorId, date);
    }

    // Get appointment by ID
    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable String id) {
        return appointmentService.getAppointmentById(id);
    }

    // Get patient's appointments
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getPatientAppointments(@PathVariable String patientId) {
        return appointmentService.getPatientAppointments(patientId);
    }

    // Cancel appointment
    @PutMapping("/{id}/cancel")
    public Appointment cancelAppointment(@PathVariable String id) {
        return appointmentService.cancelAppointment(id);
    }

    // Update appointment status
    @PutMapping("/{id}/status")
    public Appointment updateStatus(@PathVariable String id, @RequestParam String status) {
        return appointmentService.updateStatus(id, status);
    }
}