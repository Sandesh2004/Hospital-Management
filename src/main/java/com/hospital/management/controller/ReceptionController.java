package com.hospital.management.controller;

import com.hospital.management.model.Appointment;
import com.hospital.management.model.Patient;
import com.hospital.management.service.AppointmentService;
import com.hospital.management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reception")
public class ReceptionController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    // 👩‍💼 Create Patient
    @PostMapping("/patients")
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @GetMapping("/patients/{id}")
    public Patient getPatient(@PathVariable String id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/patients")
    public List<Patient> searchPatients(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return patientService.searchPatients(name);
        }
        return patientService.getAllPatients();
    }

    // Get all patients
    @GetMapping("/patients/all")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    // Update patient
    @PutMapping("/patients/{id}")
    public Patient updatePatient(@PathVariable String id, @RequestBody Patient patientDetails) {
        return patientService.updatePatient(id, patientDetails);
    }

    // Delete patient
    @DeleteMapping("/patients/{id}")
    public String deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return "Patient deleted successfully";
    }

    // 👩‍💼 Reception dashboard
    @GetMapping("/test")
    public String test() {
        return "Reception API working";
    }

    // 📅 Get all appointments for reception dashboard
    @GetMapping("/appointments")
    public Page<Appointment> getAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return appointmentService.getAllAppointments(PageRequest.of(page, size));
    }
}