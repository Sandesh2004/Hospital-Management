package com.hospital.management.controller;

import com.hospital.management.model.Patient;
import com.hospital.management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reception")
public class ReceptionController {

    @Autowired
    private PatientService patientService;

    // 👩‍💼 Create Patient
    @PostMapping("/patients")
    public String createPatient(@RequestBody Patient patient) {
        patientService.savePatient(patient);
        return "Patient created successfully";
    }

    // 👩‍💼 Reception dashboard
    @GetMapping("/test")
    public String test() {
        return "Reception API working";
    }
}